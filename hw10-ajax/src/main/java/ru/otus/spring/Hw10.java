package ru.otus.spring;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@SpringBootApplication
@EnableMongoRepositories
public class Hw10 {

    public static void main(String[] args) {
        SpringApplication.run(Hw10.class);
    }

}