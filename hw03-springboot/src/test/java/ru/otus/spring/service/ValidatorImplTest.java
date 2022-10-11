package ru.otus.spring.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Option;
import ru.otus.spring.domain.Question;

@SpringBootTest
class ValidatorImplTest {

    @Autowired
    private Validator validator;

    @Autowired
    private Reader reader;

    @MockBean
    private MainCommandLineRunner commandLineRunner;

    @Test
    void testValidateWrongAnswers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        List<String> rightAnswers = reader.read().stream().map(Question::getOptions).flatMap(Collection::stream).filter(Option::isRight).map(Option::getText).collect(Collectors.toList());
        List<String> wrongAnswers = List.of("blah-blah", "blah-blah", "blah-blah", "blah-blah");

        validator.validate(rightAnswers, wrongAnswers);

        Assertions.assertTrue(baos.toString().contains("Сорян, братишка, правильные ответы были:"));
    }

    @Test
    void testValidateRightInRightLocaleAnswers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        List<String> rightAnswers = reader.read().stream().map(Question::getOptions).flatMap(Collection::stream).filter(Option::isRight).map(Option::getText).collect(Collectors.toList());
        List<String> actualAnswers = List.of("1976", "5", "польша", "зелёный", "фиона");

        validator.validate(rightAnswers, actualAnswers);

        Assertions.assertTrue(baos.toString().contains("Отличная работа, братан!"));
    }

    @Test
    void testValidateRightInWrongLocaleAnswers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        List<String> rightAnswers = reader.read().stream().map(Question::getOptions).flatMap(Collection::stream).filter(Option::isRight).map(Option::getText).collect(Collectors.toList());
        List<String> actualAnswers = List.of("1976", "5", "poland", "green", "fIoNa");

        validator.validate(rightAnswers, actualAnswers);

        Assertions.assertFalse(baos.toString().contains("Отличная работа, братан!"));
    }

}