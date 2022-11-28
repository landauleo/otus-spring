package ru.otus.spring.dao;

import java.util.List;

import ru.otus.spring.domain.Comment;

public interface CommentDao {

    long save(Comment comment);

    Comment getById(long id);

    List<Comment> getByBookId(long bookId);

    void deleteById(long id);

}
