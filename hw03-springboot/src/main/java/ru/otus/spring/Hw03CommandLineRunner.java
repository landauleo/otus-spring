package ru.otus.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import ru.otus.spring.service.QuestionnaireService;

/*
* Теперь, кстати, при создании нового модуля в IDE можно выбирать сразу опцию : New -> Module... -> Spring Boot Initializr
 */
@Component
public class Hw03CommandLineRunner implements CommandLineRunner {

    private final QuestionnaireService questionnaireService;

    public Hw03CommandLineRunner(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Hw03CommandLineRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        questionnaireService.run();
    }

}
