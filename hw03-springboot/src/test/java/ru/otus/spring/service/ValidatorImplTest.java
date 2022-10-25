package ru.otus.spring.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.MainCommandLineRunner;
import ru.otus.spring.repository.QuestionRepository;
import ru.otus.spring.service.validator.Validator;

@SpringBootTest
class ValidatorImplTest {

    @Autowired
    private Validator validator;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private MainCommandLineRunner commandLineRunner;

    @Test
    @DisplayName("Не бросает ошибку при вводе неправильных ответов")
    void testValidateWrongAnswers() {
        List<String> rightAnswers = List.of("answer1", "answer2", "answer3", "answer4");
        List<String> wrongAnswers = List.of("blah-blah", "blah-blah", "blah-blah", "blah-blah");

        Assertions.assertDoesNotThrow(() -> validator.validate(rightAnswers, wrongAnswers));

    }

    @Test
    @DisplayName("Не бросает ошибку при вводе правильных ответов в правильной локали")
    void testValidateRightInRightLocaleAnswers() {
        List<String> rightAnswers = List.of("1976", "5", "польша", "зелёный", "фиона");
        List<String> actualAnswers = List.of("1976", "5", "польша", "зелёный", "фиона");

        Assertions.assertDoesNotThrow(() -> validator.validate(rightAnswers, actualAnswers));
    }

    @Test
    @DisplayName("Не бросает ошибку при вводе правильных ответов в неправильной локали")
    void testValidateRightInWrongLocaleAnswers() {
        List<String> rightAnswers = List.of("1976", "5", "польша", "зелёный", "фиона");
        List<String> actualAnswers = List.of("1976", "5", "poland", "green", "fIoNa");

        Assertions.assertDoesNotThrow(() -> validator.validate(rightAnswers, actualAnswers));

    }

}