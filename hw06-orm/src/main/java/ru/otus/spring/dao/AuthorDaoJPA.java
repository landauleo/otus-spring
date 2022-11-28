package ru.otus.spring.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJPA implements AuthorDao {

    private final EntityManager em;

    @Override
    public void insert(Author author) {
        em.persist(author); //javax.persistence.EntityExistsException – if the entity already exists
    }

    @Override
    public void update(Author author) {
        em.merge(author);
    }

    @Override
    public Author getById(long id) {
        TypedQuery<Author> query = em.createQuery("select id, name from Author where id = :id", Author.class).setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Author getByName(String name) {
        TypedQuery<Author> query = em.createQuery("select id, name from Author where name = :name", Author.class).setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select id, name from Author ", Author.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        TypedQuery<Author> query = em.createQuery("delete from Author where id = :id", Author.class).setParameter("id", id);
        query.executeUpdate();
    }

}
