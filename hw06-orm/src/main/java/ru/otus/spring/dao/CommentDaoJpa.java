package ru.otus.spring.dao;

import java.util.List;
import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

@Repository
@RequiredArgsConstructor
public class CommentDaoJpa implements CommentDao {

    private final EntityManager em;
    private final BookDao bookDao;

    @Override
    public long save(Comment comment, long bookId) {
        if (comment.getId() <= 0) {
            Book book = bookDao.getById(bookId);
            if (book.getComments() == null) {
                book.setComments(List.of(comment));
            } else {
                book.getComments().add(comment);
            }
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
        return em.createQuery("select c.comments from Book c " +
                " where c.id =: bookId ")
                .setParameter("bookId", bookId).getResultList();
    }

    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }

}
