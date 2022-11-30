package ru.otus.spring.dao;

import java.util.List;
import java.util.Map;
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
        return em.find(Book.class, id, Map.of("javax.persistence.fetchgraph", em.getEntityGraph("book-author-genre-entity-graph")));
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b ", Book.class);
        query.setHint("javax.persistence.fetchgraph", em.getEntityGraph("book-author-genre-entity-graph"));
        return query.getResultList();
    }

    //при createQuery мы вытягиваем всю сущность (ну только Lazy поля не тянутся)
    //при merge/remove мы можем избежать этого неоправданного действия (для некоторых разрабов это грех)
    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }


}
