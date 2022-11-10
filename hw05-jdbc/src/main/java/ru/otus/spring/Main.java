package ru.otus.spring;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.dao.PersonDao;
import ru.otus.spring.domain.Author;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);

        PersonDao dao = context.getBean(PersonDao.class);

        System.out.println("All count " + dao.count());

        dao.insert(new Author(2, "ivan"));

        System.out.println("All count " + dao.count());

        Author ivan = dao.getById(2);

        System.out.println("Ivan id: " + ivan.getId() + " name: " + ivan.getName());

        System.out.println(dao.getAll());

        Console.main(args);
    }
}
