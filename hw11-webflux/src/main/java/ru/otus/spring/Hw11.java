package ru.otus.spring;

//import io.mongock.runner.springboot.EnableMongock;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableMongock
@SpringBootApplication
@EnableReactiveMongoRepositories
public class Hw11 {

    public static void main(String[] args) {
        SpringApplication.run(Hw11.class);
    }

}
