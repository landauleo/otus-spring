package ru.otus.spring.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class CsvQuestionRepositoryImplTest {

    @MockBean
    private QuestionnaireService service;

    @MockBean
    private Validator validator;

    @Autowired
    private QuestionRepository questionRepository;

    @MockBean
    private MainCommandLineRunner commandLineRunner;

    @Test
    void testReadQuestionsNumber() {
        List<Question> read = questionRepository.read();

        assertEquals(5, read.size());
    }

}