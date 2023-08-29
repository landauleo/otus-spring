package ru.otus.spring.controller;

import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.spring.ControllerTestConfig;
import ru.otus.spring.config.SecurityConfiguration;
import ru.otus.spring.controller.dto.BookDto;
import ru.otus.spring.domain.Authority;
import ru.otus.spring.service.BookService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//ОЧЕНЬ БОЛЕЗНЕННЫЙ И НЕОЧЕВИДНЫЙ МОМЕНТ: WebMvcTest не поднимает весь контекст, соответственно свой кастомный SecurityConfiguration класс тоже нужно подтягивать
//если этого не сделать то все юзеры получают доступы до всех ручек, какие бы authorities в @WithMockUser ни были прописаны
@Import({ControllerTestConfig.class, SecurityConfiguration.class})
@DisplayName("Controller для проверки работы аутентификации")
@WebMvcTest(controllers = {BookController.class, GenreController.class, AuthorController.class, MainController.class})
@ExtendWith(SpringExtension.class)
class SecurityCheckControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private static final ObjectId ID = new ObjectId("645850e49269b4382292c9b8");
    private static final BookDto BOOK_TO_CREATE = new BookDto(null, "Amok", "novella", "Stefan Zweig");
    private static final BookDto BOOK_TO_EDIT = new BookDto(ID.toString(), "Unbekannter", "novella", "Stefan Zweig");
    private static final String BOOK_API = "/api/book";
    private static final String GENRE_API = "/api/genre";
    private static final String AUTHOR_API = "/api/author";

    private static final UserRequestPostProcessor librarian = user("Мариванна").authorities(Set.of(new Authority("ROLE_LIBRARIAN")));
    private static final UserRequestPostProcessor visitor = user("Алёша").authorities(Set.of(new Authority("ROLE_VISITOR")));
    private static final UserRequestPostProcessor anon = user("Anon").authorities(Set.of(new Authority("ROLE_NOBODY")));

    @DisplayName("Проверяет работу всех методов")
    @ParameterizedTest
    @MethodSource("provideArgs")
    void testGetHtmlPages(MockHttpServletRequestBuilder request, ResultMatcher status, BookDto bookDto) throws Exception {
        mvc.perform(bookDto != null ? request.content(objectMapper.writeValueAsString(bookDto)).contentType(MediaType.APPLICATION_JSON)
                        : request)

                .andExpect(status);
    }

    private static Stream<Arguments> provideArgs() {
        return Stream.of(
                Arguments.of(get("/error"), status().isOk(), null),
                Arguments.of(get("/index"), status().is3xxRedirection(), null),
                Arguments.of(get("/index").with(anon), status().isOk(), null),

                Arguments.of(get(GENRE_API), status().is3xxRedirection(), null),
                Arguments.of(get(BOOK_API), status().is3xxRedirection(), null),
                Arguments.of(get(AUTHOR_API), status().is3xxRedirection(), null),

                Arguments.of(get(GENRE_API).with(anon), status().isForbidden(), null),
                Arguments.of(get(BOOK_API).with(anon), status().isForbidden(), null),
                Arguments.of(get(AUTHOR_API).with(anon), status().isForbidden(), null),

                Arguments.of(get(GENRE_API).with(visitor), status().isOk(), null),
                Arguments.of(get(BOOK_API).with(visitor), status().isOk(), null),
                Arguments.of(get(AUTHOR_API).with(visitor), status().isOk(), null),

                Arguments.of(get(GENRE_API).with(librarian), status().isOk(), null),
                Arguments.of(get(BOOK_API).with(librarian), status().isOk(), null),
                Arguments.of(get(AUTHOR_API).with(librarian), status().isOk(), null),

                Arguments.of(post(BOOK_API).contentType(MediaType.APPLICATION_JSON), status().is3xxRedirection(), BOOK_TO_EDIT),
                Arguments.of(post(BOOK_API).contentType(MediaType.APPLICATION_JSON), status().is3xxRedirection(), BOOK_TO_CREATE),

                Arguments.of(post(BOOK_API).with(anon).contentType(MediaType.APPLICATION_JSON), status().isForbidden(), BOOK_TO_EDIT),
                Arguments.of(post(BOOK_API).with(anon).contentType(MediaType.APPLICATION_JSON), status().isForbidden(), BOOK_TO_CREATE),

                Arguments.of(post(BOOK_API).with(visitor).contentType(MediaType.APPLICATION_JSON), status().isForbidden(), BOOK_TO_EDIT),
                Arguments.of(post(BOOK_API).with(visitor).contentType(MediaType.APPLICATION_JSON), status().isForbidden(), BOOK_TO_CREATE),

                Arguments.of(post(BOOK_API).with(librarian).contentType(MediaType.APPLICATION_JSON), status().isOk(), BOOK_TO_EDIT),
                Arguments.of(post(BOOK_API).with(librarian).contentType(MediaType.APPLICATION_JSON), status().isOk(), BOOK_TO_CREATE),

                Arguments.of(delete(BOOK_API + "/" + ID), status().is3xxRedirection(), null),
                Arguments.of(delete(BOOK_API + "/" + ID).with(anon), status().isForbidden(), null),
                Arguments.of(delete(BOOK_API + "/" + ID).with(visitor), status().isForbidden(), null),
                Arguments.of(delete(BOOK_API + "/" + ID).with(librarian), status().isOk(), null)

        );
    }

}