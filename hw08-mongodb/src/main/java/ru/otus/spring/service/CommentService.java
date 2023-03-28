package ru.otus.spring.service;

import java.util.List;

import ru.otus.spring.domain.Comment;

public interface CommentService {

    Comment save(long commentId, long bookId, String text);

    List<Comment> getByBookId(long bookId);

    Comment getById(long id);

    void deleteById(long id);

}
