package ru.otus.spring;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@SpringBootApplication
@EnableMongoRepositories
//@EnableWebMvc когда оставляла тут эту аннотацию, а не уносила в WebMvcConfig, то
public class Hw09 {

    public static void main(String[] args) {
        SpringApplication.run(Hw09.class);
    }

}
