package ru.otus.spring.dao;

import java.util.Collections;
import java.util.List;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(BookDaoJpa.class)
@DisplayName("Dao для работы с книгами")
class BookDaoJpaTest {

    @Autowired
    private BookDaoJpa bookDaoJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Сохраняет книгу")
    void testInsert() {
        Genre genre = new Genre(1, "poem");
        Author author = new Author(1, "yoshimoto banana");
        Book book = new Book("sleeping", genre, author);

        int originalSize = bookDaoJpa.getAll().size();
        assertEquals(0, originalSize);
        long bookId = bookDaoJpa.save(book);
        book.setId(bookId);
        Book foundBook = bookDaoJpa.getById(bookId);
        int updatedSize = bookDaoJpa.getAll().size();

        assertNotEquals(originalSize, updatedSize);
        assertEquals(1, updatedSize);
        assertEquals(book, foundBook);
    }

    @Test
    @DisplayName("Изменяет книгу")
    void testUpdate() {
        Genre genre = new Genre(1, "poem");
        Author author = new Author(1, "yoshimoto banana");
        Book book = new Book("sleeping", genre, author);

        long bookId = bookDaoJpa.save(book);
        Book originalBookFromDb = bookDaoJpa.getById(bookId);
        Book updatedVersionOfBook = new Book(bookId, "tsugumi", genre, author);

        //если не детачить сущности после апдэйтов, то при следующем обращении к сущности вытянется её копия из кэша и она будет со СТАРЫМИ полями
        testEntityManager.detach(originalBookFromDb);

        bookDaoJpa.save(updatedVersionOfBook);
        Book updatedBookFromDb = bookDaoJpa.getById(bookId);

        assertEquals(updatedVersionOfBook, updatedBookFromDb);
        assertNotEquals(originalBookFromDb, updatedBookFromDb);
    }

    @Test
    @DisplayName("Получает книгу по ID")
    void testGetById() {
        Genre genre = new Genre(1, "poem");
        Author author = new Author(1, "yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        long bookId = bookDaoJpa.save(book);
        book.setId(bookId);

        Book bookFromDb = bookDaoJpa.getById(bookId);

        assertEquals(book, bookFromDb);
    }

    @Test
    @Sql(scripts = {"/testGetAll.sql"})
    @DisplayName("Получает все книги")
    void testGetAll() {
        List<Book> books = bookDaoJpa.getAll();

        assertNotEquals(Collections.emptyList(), books);
        assertEquals(5, books.size());
    }

    @Test
    @DisplayName("Удаляет книгу по ID")
    @Sql(statements = "insert into book (id, `name`, genre_id, author_id) values (100, 'moon', 1, 1);")
    void testDeleteById() {
        Book book = bookDaoJpa.getById(100);

        assertNotNull(book);
        assertDoesNotThrow(() -> bookDaoJpa.deleteById(100));
        assertThrows(NoResultException.class, () -> bookDaoJpa.getById(100));
    }
}