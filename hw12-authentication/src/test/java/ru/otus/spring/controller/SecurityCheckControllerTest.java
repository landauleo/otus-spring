package ru.otus.spring.controller;

import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.spring.ControllerTestConfig;
import ru.otus.spring.controller.dto.BookDto;
import ru.otus.spring.service.BookService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(ControllerTestConfig.class)
@DisplayName("Controller для проверки работы аутентификации")
@WebMvcTest(controllers = {BookController.class, GenreController.class, AuthorController.class, MainController.class})
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


    @DisplayName("Отдает 401 статус при запросах для неавторизованных пользователей")
    @ParameterizedTest
    @MethodSource("provideArgsForGetAndDelete")
    void testGetAndDeleteMethodsUnauthorized(MockHttpServletRequestBuilder httpServletRequestBuilder) throws Exception {
        mvc.perform(httpServletRequestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("provideArgsForGetAndDelete")
    @DisplayName("Отдает 200 статус при запросах для авторизованных пользователей")
    void testGetAndDeleteMethodsOk(MockHttpServletRequestBuilder httpServletRequestBuilder) throws Exception {
        mvc.perform(httpServletRequestBuilder)
                .andExpect(status().isOk());
    }


    @ParameterizedTest
    @DisplayName("Отдает 401 статус при запросах для неавторизованных пользователей")
    @MethodSource("provideArgsForPost")
    void testPostMethodsUnauthorized(BookDto bookDto) throws Exception {
        mvc.perform(post("/api/book")
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())) //без этого не работают все методы кроме GET https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html

                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @DisplayName("Отдает 200 статус при запросах для авторизованных пользователей")
    @WithMockUser
    @MethodSource("provideArgsForPost")
    void testPostMethodsOk(BookDto bookDto) throws Exception {
        mvc.perform(post("/api/book")
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())) //без этого не работают все методы кроме GET https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html

                .andExpect(status().isOk());
    }

    private static Stream<Arguments> provideArgsForPost() {
        return Stream.of(
                Arguments.of(BOOK_TO_CREATE),
                Arguments.of(BOOK_TO_EDIT)
        );
    }

    private static Stream<Arguments> provideArgsForGetAndDelete() {
        return Stream.of(
                Arguments.of(delete("/api/book/" + ID)
                        .with(csrf())),
                Arguments.of(get("/api/genre")),
                Arguments.of(get("/api/book")),
                Arguments.of(get("/api/author")),
                Arguments.of(get("/index")),
                Arguments.of(get("/error")),
                Arguments.of(get("/static/css/style.css")),
                Arguments.of(get("/static/images/error.jpg")),
                Arguments.of(get("/static/images/favicon.ico"))
        );
    }

}