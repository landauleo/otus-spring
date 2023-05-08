package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
//@EnableWebMvc когда оставляла тут эту аннотацию, а не уносила в WebMvcConfig, то
public class Hw09 {

    public static void main(String[] args) {
        SpringApplication.run(Hw09.class);
    }

}
