package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.config.AppProps;

/*
 * Теперь, кстати, при создании нового модуля в IDE можно выбирать сразу опцию : New -> Module... -> Spring Boot Initializr
 */
@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
public class Hw03SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hw03SpringbootApplication.class, args);
    }

}
