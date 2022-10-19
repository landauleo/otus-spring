package ru.otus.spring.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Option;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.provider.ResourceProvider;

@Repository
public class CsvQuestionRepository implements QuestionRepository {

    private final ResourceProvider resourceProvider;
    private static final Logger log = LoggerFactory.getLogger(CsvQuestionRepository.class);
    private static final int QUESTION_TEXT_CELL_INDEX = 0;
    private static final int ANSWER_CELL_INDEX = 5;

    public CsvQuestionRepository(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public List<Question> read() {
        String fileName = resourceProvider.getFilename();
        log.info("Is about read CSV-file");

        if (fileName.trim().equals("")) {
            throw new IllegalArgumentException("Filename can't be blank");
        }

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("Can't find .csv file with name " + fileName);
        }

        List<Question> questions = new ArrayList<>();

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream)).withCSVParser(parser).build()) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String questionText = getQuestionText(nextLine);
                List<Option> options = getOptions(nextLine);
                Question question = new Question(questionText, options);
                questions.add(question);
            }
        } catch (IOException | CsvValidationException e) {
            log.error(e.getMessage());
        }

        log.info("Questions list: " + questions);
        return questions;
    }

    private String getQuestionText(String[] cellsContent) {
        return cellsContent.length >= 1 ? cellsContent[QUESTION_TEXT_CELL_INDEX] : null;
    }

    private List<Option> getOptions(String[] cellsContent) {
        List<Option> options = new ArrayList<>();
        String answer;

        if (cellsContent.length > 1) {
            for (int i = 1; i < cellsContent.length - 1; i++) {
                 answer = getAnswer(cellsContent);
                String text = cellsContent[i];
                options.add(new Option(text.toLowerCase(Locale.ROOT), text.equalsIgnoreCase(answer)));
            }
        }

        return options;
    }

    private String getAnswer(String[] cellsContent) {
        if (cellsContent.length > ANSWER_CELL_INDEX) {
            return cellsContent[ANSWER_CELL_INDEX].toLowerCase(Locale.ROOT);
        }
        return null;
    }

}
