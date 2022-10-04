package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.service.QuestionnaireService;
import ru.otus.spring.service.Reader;

@ComponentScan(basePackages = "ru.otus")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();
        QuestionnaireService service = context.getBean(QuestionnaireService.class);
        service.run();
    }
}
