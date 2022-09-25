package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class QuestionnaireServiceTest {

    private QuestionnaireService service;

    @Test
    void testInnerMethodExecution() {
        Reader reader = mock(Reader.class);
        Validator validator = mock(Validator.class);
        service = new QuestionnaireServiceImpl(reader, validator);

        service.run();

        verify(reader, times(1)).read();
        verify(validator, times(1)).validate(anyList(), anyList());
    }

    @Test
    void testConsoleOutput() {
        Reader reader = mock(Reader.class);
        Validator validator = mock(Validator.class);
        service = new QuestionnaireServiceImpl(reader, validator);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        service.run();

        Assertions.assertTrue(baos.toString().contains("-<<=== Hi there! Would you mind answering 5 questions? ===>>-" ));
        Assertions.assertTrue(baos.toString().contains("Answer wisely typing the right variant in console!"));
    }

    @Test
    void testThrowsWithoutAppConfig() {
        Reader reader = new CsvReaderImpl();
        Validator validator = mock(Validator.class);
        service = new QuestionnaireServiceImpl(reader, validator);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.run());

        assertEquals(exception.getMessage(), "Filename can't be blank");
    }
}