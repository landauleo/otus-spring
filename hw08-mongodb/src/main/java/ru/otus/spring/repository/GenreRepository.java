package ru.otus.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

    Genre findByName(String name);

}
