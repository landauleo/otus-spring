package ru.otus.spring.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.MainCommandLineRunner;
import ru.otus.spring.domain.Question;
import ru.otus.spring.repository.CsvQuestionRepository;
import ru.otus.spring.repository.QuestionRepository;
import ru.otus.spring.service.provider.ResourceProvider;
import ru.otus.spring.service.validator.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
class CsvQuestionRepositoryTest {

    @MockBean
    private QuestionnaireService service;

    @MockBean
    private Validator validator;

    @Autowired
    private QuestionRepository questionRepository;

    @MockBean
    private MainCommandLineRunner commandLineRunner;

    @Mock
    private ResourceProvider resourceProvider;

    @Test
    @DisplayName("Возвращает не пустой список из 5 объектов")
    void testReadQuestionsNumber() {
        List<Question> read = questionRepository.read();

        assertFalse(read.isEmpty());
        assertEquals(5, read.size());
    }

    @Test
    @DisplayName("Возвращает список из объектов класса Question")
    void testReadQuestionsType() {
        List<Question> read = questionRepository.read();

        read.forEach(question -> assertEquals(Question.class, question.getClass()));
    }

    @Test
    @DisplayName("Кидает ошибку, если не удалось вычитать файл")
    void testReadQuestionsFromUnknownFile() {
        when(resourceProvider.getFilename()).thenReturn("Sectumsempra");

        questionRepository = new CsvQuestionRepository(resourceProvider);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> questionRepository.read());
        assertEquals(exception.getMessage(), "Can't find .csv file with name Sectumsempra");
    }

    @Test
    @DisplayName("Кидает ошибку, если имя файла пустое")
    void testReadQuestionsFromFileWithBlankName() {
        when(resourceProvider.getFilename()).thenReturn("   ");

        questionRepository = new CsvQuestionRepository(resourceProvider);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> questionRepository.read());
        assertEquals(exception.getMessage(), "Filename can't be blank");
    }

}