package ru.otus.spring.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

@Repository
public interface GenreRepository extends MongoRepository<Genre, ObjectId> {

    Optional<Genre> findByName(String name);

}
