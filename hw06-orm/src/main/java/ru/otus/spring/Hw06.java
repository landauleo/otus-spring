package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hw06 {

    //нет Shell, и поскольку это не WEB-приложение пока, то после запуска сразу происходит shutdown
    public static void main(String[] args) {
        SpringApplication.run(Hw06.class);
    }

}
