package ru.otus.spring.service;

import java.util.List;

import org.bson.types.ObjectId;
import ru.otus.spring.controller.dto.BookDto;
import ru.otus.spring.domain.Book;

public interface BookService {

    ObjectId save(BookDto bookDto);

    Book getById(ObjectId id);

    List<Book> getAll();

    void deleteById(ObjectId id);

}
