package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
//@EnableMongock  //Using @EnableMongock with minimal configuration only requires changeLog package to scan in property file
@SpringBootApplication
public class Hw11 {

    public static void main(String[] args) {
        SpringApplication.run(Hw11.class);
    }

}
