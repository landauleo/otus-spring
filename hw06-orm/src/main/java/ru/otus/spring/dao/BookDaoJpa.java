package ru.otus.spring.dao;

import java.util.List;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

@Repository
@RequiredArgsConstructor
public class BookDaoJpa implements BookDao {

    private final EntityManager em;

    @Override
    public long save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book.getId();
        } else {
            return em.merge(book).getId();
        }
    }

    @Override
    public Book getById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b " +
                "left join fetch b.genre " +
                "left join fetch  b.author " +
                "where b.id = :id", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select distinct b from Book b " +
                "left join fetch b.genre " +
                "left join fetch  b.author ", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    //при createQuery мы вытягиваем всю сущность (ну только Lazy поля не тянутся)
    //при merge/remove мы можем избежать этого неоправданного действия (для некоторых разрабов это грех)
    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }


}
