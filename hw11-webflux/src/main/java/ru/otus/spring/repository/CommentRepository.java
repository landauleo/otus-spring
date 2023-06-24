package ru.otus.spring.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, ObjectId> {

    Flux<Comment> findByBookId(ObjectId bookId);

    Mono<Void> deleteAllByBookId(ObjectId bookId);

}
