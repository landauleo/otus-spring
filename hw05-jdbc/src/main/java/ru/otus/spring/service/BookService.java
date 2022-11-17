package ru.otus.spring.service;

import java.util.List;

import ru.otus.spring.domain.Book;

public interface BookService {

    long insert(String bookName, String genreName, String authorName);

    void update(long id, String bookName, String genreName, String authorName);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);

}
