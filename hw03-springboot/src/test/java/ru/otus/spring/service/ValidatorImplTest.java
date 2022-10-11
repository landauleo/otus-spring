package ru.otus.spring.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    Reader reader;

    @MockBean
    private MainCommandLineRunner commandLineRunner;

    @Test
    void testValidateWrongAnswers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        List<Option> rightAnswers = reader.read().stream().map(Question::getAnswer).collect(Collectors.toList());
        List<Option> wrongAnswers = List.of(new Option("blah-blah"), new Option("blah-blah"), new Option("blah-blah"), new Option("blah-blah"));

        validator.validate(rightAnswers, wrongAnswers);

        Assertions.assertTrue(baos.toString().contains("Сорян, братишка, правильные ответы были:"));
    }

    @Test
    void testValidateRightInRightLocaleAnswers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        List<Option> rightAnswers = reader.read().stream().map(Question::getAnswer).collect(Collectors.toList());
        List<Option> actualAnswers = List.of(new Option("1976"), new Option("5"), new Option("польша"), new Option("зелёный"), new Option("фиона"));

        validator.validate(rightAnswers, actualAnswers);

        Assertions.assertTrue(baos.toString().contains("Отличная работа, братан!"));
    }

    @Test
    void testValidateRightInWrongLocaleAnswers() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        List<Option> rightAnswers = reader.read().stream().map(Question::getAnswer).collect(Collectors.toList());
        List<Option> actualAnswers = List.of(new Option("1976"), new Option("5"), new Option("poland"), new Option("green"), new Option("fIoNa"));

        validator.validate(rightAnswers, actualAnswers);

        Assertions.assertFalse(baos.toString().contains("Отличная работа, братан!"));
    }

}