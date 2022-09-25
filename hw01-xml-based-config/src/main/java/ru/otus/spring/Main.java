package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.Reader;

/**
 * java -jar hw01-xml-jar-with-dependencies.jar
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        Reader service = context.getBean(Reader.class);
        service.read();
        // Данная операция, в принципе не нужна.
        // Мы не работаем пока что с БД, а Spring Boot сделает закрытие за нас
        context.close();
    }
}
