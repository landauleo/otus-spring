package ru.otus.spring.dao;

import java.util.List;
import javax.persistence.EntityManager;

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

    @Override
    public Comment getById(long id) {
        return em.find(Comment.class, id);

    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        System.out.println(em.createQuery("select c from Comment c " +
//                "left join fetch c.book " +
//                "left join fetch c.book.genre " +
//                "left join fetch c.book.author " +
                " where c.book.id = :bookId", Comment.class)
                .setParameter("bookId", bookId).getResultList().size());

        return em.createQuery("select c from Comment c " +
//                "left join fetch c.book " +
//                "left join fetch c.book.genre " +
//                "left join fetch c.book.author " +
                " where c.book.id = :bookId", Comment.class)
                .setParameter("bookId", bookId).getResultList();
    }

    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }

}
