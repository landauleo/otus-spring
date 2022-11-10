package ru.otus.spring;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.Collections;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        //TODO change to shell
        ApplicationContext context = SpringApplication.run(Main.class);

        AuthorDao dao = context.getBean(AuthorDao.class);

        Author ivan = dao.getById(1);

        System.out.println("getByIdgetById: " + ivan.getId() + " name: " + ivan.getName());

        System.out.println(dao.getAll());
        dao.insert(new Author(2, "sss", Collections.emptySet()));

        System.out.println(dao.getAll());

        dao.deleteById(2);
        System.out.println(dao.getAll());
        dao.update(new Author(1, "new_name", Collections.emptySet()));

        System.out.println(dao.getAll());
        Console.main(args);
    }
}
