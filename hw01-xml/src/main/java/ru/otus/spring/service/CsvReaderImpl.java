package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Option;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CsvReaderImpl implements Reader {

    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Option> read() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("Can't find .csv file with name " + fileName);
        }

        List<Option> options = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine != null) {
                    System.out.println(Arrays.toString(nextLine));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return options;
    }
}
