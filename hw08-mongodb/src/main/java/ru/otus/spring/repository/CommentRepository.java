package ru.otus.spring.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, Long> {

    List<Comment> findByBookId(@Param("bookId") long bookId);

    Comment getById(Long id);

    Comment findTopByOrderByIdDesc();

    void deleteAllByBook(Book book);

}
