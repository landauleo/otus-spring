package ru.otus.spring.service;

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
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionnaireServiceImpl service;

    @Autowired
    private AppProps appProps;

    @Test
    void testDefineLocale() {
        Assertions.assertNotEquals(Locale.ENGLISH, appProps.getLocale());
        Assertions.assertEquals("RU", appProps.getLocale().getCountry());
    }

}