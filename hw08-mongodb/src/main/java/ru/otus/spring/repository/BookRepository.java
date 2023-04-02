package ru.otus.spring.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId> {

    List<Book> findAll();

    Book getById(ObjectId id);

}
