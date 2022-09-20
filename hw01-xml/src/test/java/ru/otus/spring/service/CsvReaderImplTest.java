package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ImportResource;
import ru.otus.spring.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ImportResource(value = "/test-spring-context.xml")
class CsvReaderImplTest {

    private static CsvReaderImpl service;

    @Test
    void readSuccessfullyTest() {
        service = new CsvReaderImpl();
        service.setFileName("questionnaire.csv");

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