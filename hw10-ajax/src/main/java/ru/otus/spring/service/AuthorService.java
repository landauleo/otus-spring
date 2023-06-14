package ru.otus.spring.service;

import java.util.List;

import ru.otus.spring.domain.Author;

public interface AuthorService {

    Author getByName(String name);

    List<Author> getAll();

}
