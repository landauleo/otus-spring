package ru.otus.spring.dao;

import java.util.List;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

public interface GenreDao {

    void insert(Genre book);

    void update(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);

}
