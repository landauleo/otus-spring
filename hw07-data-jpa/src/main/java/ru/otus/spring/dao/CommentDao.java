package ru.otus.spring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Comment;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {

    @EntityGraph(value = "book-author-genre-entity-graph")
    @Query("select c from Comment c where c.book.id = :bookId")
     List<Comment> findByBookId(@Param("bookId") long bookId);

}
