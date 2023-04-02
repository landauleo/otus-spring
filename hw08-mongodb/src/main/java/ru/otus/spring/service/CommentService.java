package ru.otus.spring.service;

import java.util.List;

import org.bson.types.ObjectId;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

public interface CommentService {

    Comment save(ObjectId commentId, ObjectId bookId, String text);

    List<Comment> getByBookId(ObjectId bookId);

    Comment getById(ObjectId id);

    void deleteById(ObjectId id);

}
