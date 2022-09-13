package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ImportResource(value = "/test-spring-context.xml")
class CsvReaderImplTest {

    private static CsvReaderImpl service;

    @BeforeEach
    void init() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/test-spring-context.xml");
        service = context.getBean(CsvReaderImpl.class);
    }

    @Test
    void readSuccessfullyTest() {
        List<Question> questions = service.read();
        assertNotNull(questions);
        assertDoesNotThrow(() -> service.read());
        assertEquals(questions.size(), 5);
    }

    @Test
    void readWithAbsentFilenameTest() {
        service = new CsvReaderImpl();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.read());
        assertEquals(exception.getMessage(), "Filename can't be blank");
    }

    @Test
    void readWithUnknownFilenameTest() {
        service = new CsvReaderImpl();
        service.setFileName("Alohomora!");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.read());
        assertEquals(exception.getMessage(), "Can't find .csv file with name Alohomora!");
    }

}