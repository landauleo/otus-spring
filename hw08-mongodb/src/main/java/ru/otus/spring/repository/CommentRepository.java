package ru.otus.spring.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

    List<Comment> findByBookId(@Param("bookId") ObjectId bookId);

    Comment getById(ObjectId id);

    void deleteAllByBook(Book book);

}
