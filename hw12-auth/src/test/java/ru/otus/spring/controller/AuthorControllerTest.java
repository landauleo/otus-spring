package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestConfig.class)
@DisplayName("Controller для работы с авторами")
@WebMvcTest(controllers = AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Отдает 401 статус при запросе на получение всех авторов для неавторизованных пользователей")
    void getAllAuthorsUnauthorizedStatusTest() throws Exception {
        mvc.perform(get("/api/author"))

                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("Отдает 200 статус при запросе на получение всех авторов для авторизованных пользователей")
    void getAllAuthorsOkStatusTest() throws Exception {
        mvc.perform(get("/api/author"))

                .andExpect(status().isOk());
    }

}