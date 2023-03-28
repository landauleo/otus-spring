package ru.otus.spring.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
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

    @Test
    @DisplayName("Сохраняет книгу")
    void testInsert() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);

        int originalSize = bookRepository.findAll().size();
        assertEquals(0, originalSize);
        long bookId = bookRepository.save(book).getId();
        book.setId(bookId);
        Book foundBook = bookRepository.findById(bookId).get();
        int updatedSize = bookRepository.findAll().size();

        assertNotEquals(originalSize, updatedSize);
        assertEquals(1, updatedSize);
        assertEquals(book, foundBook);
    }

    @Test
    @DisplayName("Сохраняет несколько книг")
    void testInsertMultiple() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book firstBook = new Book(1, "sleeping", genre, author);
        Book secondBook = new Book(2, "amrita", genre, author);

        int originalSize = bookRepository.findAll().size();
        bookRepository.save(firstBook);
        bookRepository.save(secondBook);
        int updatedSize = bookRepository.findAll().size();

        assertEquals(0, originalSize);
        assertEquals(2, updatedSize);
    }

    @Test
    @DisplayName("Изменяет книгу")
    void testUpdate() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        long bookId = 1;
        Book book = new Book(bookId, "sleeping", genre, author);

        bookRepository.save(book);
        Book originalBookFromDb = bookRepository.save(book);
        Book updatedVersionOfBook = new Book(bookId, "tsugumi", genre, author);

        //тут валилась ошибка изначально, потому что bookId = 0, из-за чего mongo воспринимал это как новую сущность https://stackoverflow.com/a/71349538
        bookRepository.save(updatedVersionOfBook);
        Book updatedBookFromDb = bookRepository.findById(bookId).get();

        assertEquals(updatedVersionOfBook, updatedBookFromDb);
        assertNotEquals(originalBookFromDb, updatedBookFromDb);
    }

    @Test
    @DisplayName("Получает книгу по ID")
    void testGetById() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        long bookId = bookRepository.save(book).getId();
        book.setId(bookId);

        Book bookFromDb = bookRepository.getById(bookId);

        assertEquals(book, bookFromDb);
    }

    //тут в отличие от прошлого дз пришлось убрать @Sql, потому что в этом модуле в зависимостях больше нет spring-jdbc
    @Test
    @DisplayName("Получает все книги")
    void testGetAll() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        bookRepository.insert(List.of(
                new Book(1, "kitchen", genre, author),
                new Book(2, "harboiled/hardluck", genre, author),
                new Book(3, "moshi moshi", genre, author),
                new Book(4, "amrita", genre, author),
                new Book(5, "honeymoon", genre, author)));
        List<Book> books = bookRepository.findAll();

        assertNotEquals(Collections.emptyList(), books);
        assertEquals(5, books.size());
    }

    @Test
    @DisplayName("Удаляет книгу по ID")
    void testDeleteById() {
        long bookId = 100;
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = bookRepository.save(new Book(bookId, "sleeping", genre, author));

        assertNotNull(book);
        assertDoesNotThrow(() -> bookRepository.deleteById(bookId));
        assertEquals(Optional.empty(), bookRepository.findById(bookId));
    }

}