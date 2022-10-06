package ru.otus.spring.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.config.AppProps;

@SpringBootTest
class QuestionnaireServiceImplTest {

    @MockBean
    private Validator validator;

    @MockBean
    private Reader reader;

    @Autowired
    private QuestionnaireServiceImpl service;

    @Autowired
    private AppProps appProps;

    @Test
    void testDefineLocale() {
        Assertions.assertNotEquals(Locale.ENGLISH, appProps.getLocale());
        Assertions.assertEquals("RU", appProps.getLocale().getCountry());
    }

    @Test
    void testGreetingText() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        service.run();

        Assertions.assertTrue(baos.toString().contains("-<<=== Приветики! Не против ответить на 5 вопросов (◕‿◕)? ===>>-"));
        Assertions.assertTrue(baos.toString().contains("Отвечай вдумчиво, печатая свои варианты в консоль!"));
    }

}