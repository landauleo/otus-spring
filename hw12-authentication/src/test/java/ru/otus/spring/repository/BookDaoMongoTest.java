package ru.otus.spring.repository;

import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@DisplayName("Dao для работы с книгами")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookDaoMongoTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Сохраняет книгу")
    void testInsert(@Autowired MongoTemplate mongoTemplate) {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);

        int originalSize = mongoTemplate.findAll(Book.class).size();
        assertEquals(0, originalSize);
        ObjectId bookId = bookRepository.save(book).getId();
        book.setId(bookId);
        Book foundBook = mongoTemplate.findById(bookId, Book.class);
        int updatedSize = mongoTemplate.findAll(Book.class).size();

        assertNotEquals(originalSize, updatedSize);
        assertEquals(1, updatedSize);
        assertEquals(book, foundBook);
    }

    @Test
    @DisplayName("Сохраняет несколько книг")
    void testInsertMultiple(@Autowired MongoTemplate mongoTemplate) {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book firstBook = new Book("sleeping", genre, author);
        Book secondBook = new Book("amrita", genre, author);

        int originalSize = mongoTemplate.findAll(Book.class).size();
        bookRepository.save(firstBook);
        bookRepository.save(secondBook);
        int updatedSize = mongoTemplate.findAll(Book.class).size();

        assertEquals(0, originalSize);
        assertEquals(2, updatedSize);
    }

    @Test
    @DisplayName("Изменяет книгу")
    void testUpdate() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        ObjectId bookId = new ObjectId();
        Book book = new Book(bookId, "sleeping", genre, author);

        Book originalBookFromDb = bookRepository.save(book);
        Book updatedVersionOfBook = new Book(bookId, "tsugumi", genre, author);

        Book updatedBookFromDb = bookRepository.save(updatedVersionOfBook);

        assertEquals(updatedVersionOfBook, updatedBookFromDb);
        assertNotEquals(originalBookFromDb, updatedBookFromDb);
    }

    @Test
    @DisplayName("Получает книгу по ID")
    void testGetById(@Autowired MongoTemplate mongoTemplate) {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        ObjectId bookId = mongoTemplate.save(book).getId();
        book.setId(bookId);

        Book bookFromDb = bookRepository.findById(bookId).get();

        assertEquals(book, bookFromDb);
    }

    //тут в отличие от прошлого дз пришлось убрать @Sql, потому что в этом модуле в зависимостях больше нет spring-jdbc
    @Test
    @DisplayName("Получает все книги")
    void testGetAll(@Autowired MongoTemplate mongoTemplate) {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        mongoTemplate.insertAll(List.of(
                new Book("kitchen", genre, author),
                new Book("harboiled/hardluck", genre, author),
                new Book("moshi moshi", genre, author),
                new Book("amrita", genre, author),
                new Book("honeymoon", genre, author)));
        List<Book> books = bookRepository.findAll();

        assertNotEquals(Collections.emptyList(), books);
        assertEquals(5, books.size());
    }

    @Test
    @DisplayName("Удаляет книгу по ID")
    void testDeleteById(@Autowired MongoTemplate mongoTemplate) {
        ObjectId bookId = new ObjectId();
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = mongoTemplate.save(new Book(bookId, "sleeping", genre, author));

        assertNotNull(book);
        assertDoesNotThrow(() -> bookRepository.deleteById(bookId));
        assertEquals(null, mongoTemplate.findById(bookId, Book.class));
    }

}