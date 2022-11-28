package ru.otus.spring.service;

import java.util.List;

import ru.otus.spring.domain.Comment;

public interface CommentService {

    long save(long bookId, String text);

    List<Comment> getByBookId(long bookId);

    Comment getById(long id);

    void deleteById(long id);

}
