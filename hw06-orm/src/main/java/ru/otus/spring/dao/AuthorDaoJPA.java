package ru.otus.spring.dao;

import java.util.List;
import javax.persistence.EntityManager;

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
        return em.find(Author.class, id);
    }

    @Override
    public Author getByName(String name) {
        return em.createQuery("select a from Author a where a.name = :name", Author.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        em.remove(getById(id));
    }

}
