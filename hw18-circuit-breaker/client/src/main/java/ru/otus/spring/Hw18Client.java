package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.otus.spring.rest.BookClient;

@EnableFeignClients(clients = {BookClient.class})
@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrix
public class Hw18Client {

    public static void main(String[] args) {
        SpringApplication.run(Hw18Client.class);
    }

}
