package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

//DAO would be considered closer to the database, often table-centric.
//Repository would be considered closer to the Domain, dealing only in Aggregate Roots.
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
