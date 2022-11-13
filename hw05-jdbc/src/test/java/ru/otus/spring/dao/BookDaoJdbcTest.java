package ru.otus.spring.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(BookDaoJdbc.class)
@DisplayName("Dao для работы с книгами")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    @DisplayName("Сохраняет книгу")
    @Order(1)
    void testInsert() {
        Genre genre = new Genre(1, "poem");
        Author author = new Author(1, "yoshimoto banana");
        Book book = new Book(2, "sleeping", genre, author);


        int originalSize = bookDaoJdbc.getAll().size();
        assertEquals(1, originalSize);
        long bookId = bookDaoJdbc.insert(book);
        Book foundBook = bookDaoJdbc.getById(bookId);
        int updatedSize = bookDaoJdbc.getAll().size();

        assertEquals(2, bookId);
        assertNotEquals(originalSize, updatedSize);
        assertEquals(2, updatedSize);
        assertEquals(book, foundBook);
    }

    @Test
    @DisplayName("Изменяет книгу")
    void testUpdate() {
        Genre genre = new Genre(1, "poem");
        Author author = new Author(1, "yoshimoto banana");
        Book updatedVersionOfBook = new Book(1, "tsugumi", genre, author);

        Book originalBook = bookDaoJdbc.getById(1);
        int size = bookDaoJdbc.getAll().size();

        bookDaoJdbc.update(updatedVersionOfBook);
        Book updatedBookFromDb = bookDaoJdbc.getById(1);

        assertEquals(updatedVersionOfBook, updatedBookFromDb);
        assertNotEquals(originalBook, updatedBookFromDb);
        assertEquals(1, size);
    }

    @Test
    @DisplayName("Полоучает книгу по ID")
    @Order(2)
    void testGetById() {
        Genre genre = new Genre(1, "poem");
        Author author = new Author(1, "yoshimoto banana");
        Book book = new Book(3, "sleeping", genre, author);
        long bookId = bookDaoJdbc.insert(book);

        Book bookFromDb = bookDaoJdbc.getById(bookId);

        assertEquals(book, bookFromDb);
    }

    @Test
    @Sql(scripts = {"/testGetAll.sql"})
    @DisplayName("Получает все книги")
    void testGetAll() {
        List<Book> books = bookDaoJdbc.getAll();

        assertNotEquals(Collections.emptyList(), books);
        assertNotEquals(5, books.size());
    }

    @Test
    @DisplayName("Удаляет книгу по ID")
    @Sql(statements = "insert into book (id, `name`, genre_id, author_id) values (100, 'moon', 1, 1);")
    void testDeleteById() {
        Book book = bookDaoJdbc.getById(100);

        assertNotNull(book);
        assertDoesNotThrow(() -> bookDaoJdbc.deleteById(100));
        assertThrows(EmptyResultDataAccessException.class, () -> bookDaoJdbc.getById(100));
    }
}