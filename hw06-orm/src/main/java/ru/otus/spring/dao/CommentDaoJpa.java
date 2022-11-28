package ru.otus.spring.dao;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Comment;

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

    //https://www.bychkov.name/java-ee-tutorial/persistence-entitygraphs002.html - getEntityGraph VS. createEntityGraph
    @Override
    public Comment getById(long id) {
        Map<String, Object> props = Map.of("javax.persistence.fetchgraph", em.getEntityGraph("comment-book-entity-graph"));
        return em.find(Comment.class, id, props);
    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class)
                .setParameter("bookId", bookId)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("comment-book-entity-graph"));

        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Comment c " +
                "where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
