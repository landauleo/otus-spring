package ru.otus.spring;

//import io.mongock.runner.springboot.EnableMongock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

//@EnableMongock
@SpringBootApplication
@EnableReactiveMongoRepositories //ВАЖНО!!!
//@EnableMongoRepositories
//@EnableWebFlux
public class Hw11 {

    public static void main(String[] args) {
        SpringApplication.run(Hw11.class);
    }

}
