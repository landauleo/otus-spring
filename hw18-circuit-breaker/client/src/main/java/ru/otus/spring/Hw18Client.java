package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.otus.spring.service.BookClient;

@SpringBootApplication
@EnableFeignClients(clients = {BookClient.class})
public class Hw18Client {

    public static void main(String[] args) {
        SpringApplication.run(Hw18Client.class);
    }

}
