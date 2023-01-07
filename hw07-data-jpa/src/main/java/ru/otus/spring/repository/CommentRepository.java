package ru.otus.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //если хочется всё таки нативный запрос, то можно использовать JOIN FETCH
    //JOIN VS. JOIN FETCH
    //JOIN - returns all comments for the Hibernate
    //JOIN FETCH - returns all comments AND all books associated
     List<Comment> findByBookId(@Param("bookId") long bookId);

}
