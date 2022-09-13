package ru.otus.spring.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import ru.otus.spring.domain.Option;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionText;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderImpl implements Reader {

    private String fileName;

    private static final int QUESTION_TEXT_CELL_INDEX = 0;
    private static final int ANSWER_CELL_INDEX = 5;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> read() {
        if (fileName == null || fileName.trim().equals("")) {
            throw new IllegalArgumentException("Filename can't be blank");
        }

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("Can't find .csv file with name " + fileName);
        }

        List<Question> questions = new ArrayList<>();
        try {
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream)).withCSVParser(parser).build();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                QuestionText questionText = getQuestionText(nextLine);
                List<Option> options = getOptions(nextLine);
                Option answer = getAnswer(nextLine);
                Question question = new Question(questionText, options, answer);
                questions.add(question);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private QuestionText getQuestionText(String[] cellsContent) {
        return cellsContent.length >= 1 ? new QuestionText(cellsContent[QUESTION_TEXT_CELL_INDEX]) : null;
    }

    private List<Option> getOptions(String[] cellsContent) {
        List<Option> options = new ArrayList<>();

        if (cellsContent.length > 1) {
            for (int i = 1; i < cellsContent.length; i++) {
                options.add(new Option(cellsContent[i]));
            }
        }

        return options;
    }

    private Option getAnswer(String[] cellsContent) {
        if (cellsContent.length > ANSWER_CELL_INDEX) {
            return new Option(cellsContent[ANSWER_CELL_INDEX]);
        }
        return null;
    }
}
