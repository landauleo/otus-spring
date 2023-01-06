package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

@Repository
public interface AuthorDao extends JpaRepository<Author, Long> {

    Author getAuthorByName(String name);

}
