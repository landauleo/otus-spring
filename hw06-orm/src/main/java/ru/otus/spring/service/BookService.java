package ru.otus.spring.service;

import java.util.List;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

public interface BookService {

    long save(long id, String bookName, String genreName, String authorName);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);

}
