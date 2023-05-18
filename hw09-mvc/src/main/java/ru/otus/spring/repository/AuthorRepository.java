package ru.otus.spring.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, ObjectId> {

    Optional<Author> findByName(String name);

}
