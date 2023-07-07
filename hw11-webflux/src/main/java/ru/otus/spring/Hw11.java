package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.reactive.config.EnableWebFlux;
import ru.otus.spring.changelog.DatabaseChangelog;

@EnableWebFlux
@SpringBootApplication
public class Hw11 {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Hw11.class);
        DatabaseChangelog dbInitializer = context.getBean(DatabaseChangelog.class);

        dbInitializer.clearDb();
        dbInitializer.insertEntities();
    }

}
