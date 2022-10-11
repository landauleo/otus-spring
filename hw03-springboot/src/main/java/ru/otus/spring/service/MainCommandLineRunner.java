package ru.otus.spring.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import ru.otus.spring.service.QuestionnaireService;

/*
 * Теперь, кстати, при создании нового модуля в IDE можно выбирать сразу опцию : New -> Module... -> Spring Boot Initializr
 */
@Component
public class MainCommandLineRunner implements CommandLineRunner {

    private final QuestionnaireService questionnaireService;

    public MainCommandLineRunner(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @Override
    public void run(String... args) {
        questionnaireService.run();
    }

}
