package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

@Repository
public interface GenreDao extends JpaRepository<Genre, Long> {

    Genre getGenreByName(String name);

}
