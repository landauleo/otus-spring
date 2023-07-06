package ru.otus.spring.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Genre;

@Repository
public interface GenreRepository extends ReactiveMongoRepository<Genre, ObjectId> {

    Mono<Genre> findByName(String name);

}
