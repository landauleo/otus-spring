package ru.otus.spring.controller;

import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    private UserRequestPostProcessor librarian = user("Мариванна").authorities(Set.of(new Authority("ROLE_LIBRARIAN")));
    private UserRequestPostProcessor visitor = user("Алёша").authorities(Set.of(new Authority("ROLE_VISITOR")));
    private UserRequestPostProcessor anon = user("Anon").authorities(Set.of(new Authority("ROLE_NOBODY")));

    @DisplayName("Проверяет работу с доступом html-страниц")
    @Test
    void testGetHtmlPages() throws Exception {
        mvc.perform(get("/error"))
                .andExpect(status().isOk());

        mvc.perform(get("/index"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/index")
                        .with(anon))
                .andExpect(status().isOk());
    }


    @DisplayName("Проверяет работу GET методов")
    @ParameterizedTest
    @MethodSource("provideArgsForGet")
    void testGetMethods(MockHttpServletRequestBuilder httpServletRequestBuilder) throws Exception {
        mvc.perform(httpServletRequestBuilder)
                .andExpect(status().is3xxRedirection());

        mvc.perform(httpServletRequestBuilder
                        .with(anon))
                .andExpect(status().isForbidden());

        mvc.perform(httpServletRequestBuilder
                        .with(librarian))
                .andExpect(status().isOk());

        mvc.perform(httpServletRequestBuilder
                        .with(visitor))
                .andExpect(status().isOk());
    }

    @DisplayName("Проверяет работу POST методов")
    @ParameterizedTest
    @MethodSource("provideArgsForPost")
    void testPostMethods(BookDto bookDto) throws Exception {
        mvc.perform(post("/api/book")
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/api/book")
                        .with(anon)
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        mvc.perform(post("/api/book")
                        .with(visitor)
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        mvc.perform(post("/api/book")
                        .with(librarian)
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Проверяет работу DELETE методов")
    @Test
    void testDeleteMethods() throws Exception {
        mvc.perform(delete("/api/book" + ID))
                .andExpect(status().is3xxRedirection());

        mvc.perform(delete("/api/book" + ID)
                        .with(anon))
                .andExpect(status().isForbidden());

        mvc.perform(delete("/api/book" + ID)
                        .with(visitor))
                .andExpect(status().isForbidden());

        mvc.perform(delete("/api/book/" + ID)
                        .with(librarian))
                .andExpect(status().isOk());
    }

    private static Stream<Arguments> provideArgsForPost() {
        return Stream.of(
                Arguments.of(BOOK_TO_CREATE),
                Arguments.of(BOOK_TO_EDIT)
        );
    }

    private static Stream<Arguments> provideArgsForGet() {
        return Stream.of(
                Arguments.of(get("/api/genre")),
                Arguments.of(get("/api/book")),
                Arguments.of(get("/api/author"))
        );
    }

}