package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.CsvReaderImpl;
import ru.otus.spring.service.Reader;

@Configuration
public class AppConfig {

    @Value("${questionnaire.path}")
    private String fileName;

    @Bean
    public Reader reader() {
        CsvReaderImpl reader = new CsvReaderImpl();
        reader.setFileName(fileName);
        return reader;
    }
}
