package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CommentDaoJpa implements CommentDao {

    private final EntityManager em;

    @Override
    public long save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment.getId();
        } else {
            return em.merge(comment).getId();
        }
    }

    @Override
    public Comment getById(long id) {
        em.find(Book.class, id, Map.of("javax.persistence.fetchgraph", em.getEntityGraph("book-author-genre-entity-graph")));
        return em.find(Comment.class, id);

    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        em.find(Book.class, bookId, Map.of("javax.persistence.fetchgraph", em.getEntityGraph("book-author-genre-entity-graph")));
        return em.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class)
                .setParameter("bookId", bookId).getResultList();
    }

    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }

}
