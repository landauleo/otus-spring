package ru.otus.spring.dao;

import java.util.List;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

public interface BookDao {

    void insert(Book book, Genre genre, Author author);

    void update(Book book, Genre genre, Author author);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);

}
