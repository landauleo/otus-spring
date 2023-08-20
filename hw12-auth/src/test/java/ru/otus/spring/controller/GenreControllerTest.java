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
@DisplayName("Controller для работы с жанрами")
@WebMvcTest(controllers = GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Отдает 401 статус при запросе на получение всех жанров для неавторизованных пользователей")
    void getAllGenresUnauthorizedStatusTest() throws Exception {
        mvc.perform(get("/api/genre"))

                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("Отдает 200 статус при запросе на получение всех жанров для авторизованных пользователей")
    void getAllGenresOkStatusTest() throws Exception {
        mvc.perform(get("/api/genre"))

                .andExpect(status().isOk());
    }

}