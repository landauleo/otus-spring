package ru.otus.spring.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

    List<Comment> findByBookId(ObjectId bookId);

    void deleteAllByBookId(ObjectId bookId);

}
