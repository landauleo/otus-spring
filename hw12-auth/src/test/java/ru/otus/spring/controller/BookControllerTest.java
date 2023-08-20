package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.controller.dto.BookDto;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestConfig.class)
@DisplayName("Controller для работы с книгами")
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    private static final ObjectId ID = new ObjectId("645850e49269b4382292c9b8");
    private static final BookDto BOOK_TO_CREATE = new BookDto(null, "Amok", "novella", "Stefan Zweig");
    private static final BookDto BOOK_TO_EDIT = new BookDto(ID.toString(), "Unbekannter", "novella", "Stefan Zweig");
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Отдает 401 статус при запросе на получение всех книг для неавторизованных пользователей")
    void getAllBooksUnauthorizedStatusTest() throws Exception {
        mvc.perform(get("/api/book"))

                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("Отдает 200 статус при запросе на получение всех книг для авторизованных пользователей")
    void getAllBooksOkStatusTest() throws Exception {
        mvc.perform(get("/api/book"))

                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Отдает 401 статус при запросе на редактирование для неавторизованных пользователей")
    void editBookUnauthorizedStatusTest() throws Exception {
        mvc.perform(post("/api/book")
                        .content(objectMapper.writeValueAsString(BOOK_TO_EDIT))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())) //без этого не работают все методы кроме GET https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html

                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("Отдает 200 статус при запросе на редактирование для авторизованных пользователей")
    void editBookOkStatusTest() throws Exception {
        mvc.perform(post("/api/book")
                        .content(objectMapper.writeValueAsString(BOOK_TO_EDIT))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())) //без этого не работают все методы кроме GET https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Отдает 401 статус при запросе на удаление для неавторизованных пользователей")
    void deleteBookUnauthorizedStatusTest() throws Exception {
        mvc.perform(delete("/api/book/" + ID)
                        .with(csrf())) //без этого не работают все методы кроме GET https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html

                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("Отдает 200 статус при запросе на удаление для авторизованных пользователей")
    void deleteBookOkStatusTest() throws Exception {
        mvc.perform(delete("/api/book/" + ID)
                        .with(csrf())) //без этого не работают все методы кроме GET https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html

                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Отдает 401 статус при запросе на создание для неавторизованных пользователей")
    void createBookUnauthorizedStatusTest() throws Exception {
        mvc.perform(post("/api/book")
                        .content(objectMapper.writeValueAsString(BOOK_TO_CREATE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())) //без этого не работают все методы кроме GET https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html

                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("Отдает 200 статус при запросе на удаление для авторизованных пользователей")
    void createBookOkStatusTest() throws Exception {
        mvc.perform(post("/api/book")
                        .content(objectMapper.writeValueAsString(BOOK_TO_CREATE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())) //без этого не работают все методы кроме GET https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html

                .andExpect(status().isOk());
    }

}