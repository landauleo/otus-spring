package ru.otus.spring.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

@Repository
@RequiredArgsConstructor
public class GenreDaoJpa implements GenreDao {

    private final EntityManager em;

    @Override
    public void insert(Genre genre) {
        em.persist(genre);
    }

    @Override
    public void update(Genre genre) {
        em.merge(genre);
    }

    @Override
    public Genre getById(long id) {
        TypedQuery<Genre> query = em.createQuery("select id, name from Genre where id = :id", Genre.class).setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select id, name from Genre where name = :name", Genre.class).setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select id, name from Genre ", Genre.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        TypedQuery<Genre> query = em.createQuery("delete from Genre where id = :id", Genre.class).setParameter("id", id);
        query.executeUpdate();
    }

}
