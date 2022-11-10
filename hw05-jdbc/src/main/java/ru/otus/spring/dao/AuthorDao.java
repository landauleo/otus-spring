package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {

    void insert(Author author);

    void update(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);
}
