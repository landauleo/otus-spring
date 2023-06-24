package ru.otus.spring.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, ObjectId> {

}
