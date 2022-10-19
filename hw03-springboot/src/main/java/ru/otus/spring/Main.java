package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Теперь, кстати, при создании нового модуля в IDE можно выбирать сразу опцию : New -> Module... -> Spring Boot Initializr
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
