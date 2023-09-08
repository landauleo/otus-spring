package ru.otus.spring.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Authority;

@Repository
public interface AuthorityRepository extends MongoRepository<Authority, ObjectId> {

    Authority findByName(String name);

}
