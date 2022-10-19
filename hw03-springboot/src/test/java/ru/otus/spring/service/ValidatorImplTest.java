package ru.otus.spring.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.MainCommandLineRunner;
import ru.otus.spring.domain.Option;
import ru.otus.spring.domain.Question;
import ru.otus.spring.repository.QuestionRepository;
import ru.otus.spring.service.validator.Validator;

@SpringBootTest
class ValidatorImplTest {

    @Autowired
    private Validator validator;

    @Autowired
    private QuestionRepository questionRepository;

    @MockBean
    private MainCommandLineRunner commandLineRunner;

    @Test
    void testValidateWrongAnswers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        List<String> rightAnswers = questionRepository.read().stream().map(Question::getOptions).flatMap(Collection::stream).filter(Option::isRight).map(Option::getText).collect(Collectors.toList());
        List<String> wrongAnswers = List.of("blah-blah", "blah-blah", "blah-blah", "blah-blah");

        Assertions.assertDoesNotThrow(() -> validator.validate(rightAnswers, wrongAnswers));

    }

    @Test
    void testValidateRightInRightLocaleAnswers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        List<String> rightAnswers = questionRepository.read().stream().map(Question::getOptions).flatMap(Collection::stream).filter(Option::isRight).map(option -> option.getText().toLowerCase(Locale.ROOT)).collect(Collectors.toList());
        List<String> actualAnswers = List.of("1976", "5", "польша", "зелёный", "фиона");

        Assertions.assertDoesNotThrow(() -> validator.validate(rightAnswers, actualAnswers));
    }

    @Test
    void testValidateRightInWrongLocaleAnswers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        List<String> rightAnswers = questionRepository.read().stream().map(Question::getOptions).flatMap(Collection::stream).filter(Option::isRight).map(Option::getText).collect(Collectors.toList());
        List<String> actualAnswers = List.of("1976", "5", "poland", "green", "fIoNa");

        Assertions.assertDoesNotThrow(() -> validator.validate(rightAnswers, actualAnswers));

    }

}