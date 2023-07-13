package ru.otus.spring.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, ObjectId> {

}
