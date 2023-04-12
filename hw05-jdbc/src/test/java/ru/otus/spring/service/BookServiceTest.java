package ru.otus.spring.service;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(BookServiceImpl.class)
@DisplayName("Service для работы с книгами")
class BookServiceTest {

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @MockBean
    private BookDao bookDao;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Сохраняет книгу")
    void insert() {
        when(authorService.getByName(anyString())).thenReturn(new Author(1, "yoshimoto banana"));
        when(genreService.getByName(anyString())).thenReturn(new Genre(1, "poem"));
        when(bookDao.insert(any())).thenReturn(2L);
        long expectedId = 2L;
        long finalId = bookService.insert("moshi moshi", "poem", "yoshimoto banana");

        assertEquals(expectedId, finalId);
    }

    @Test
    @DisplayName("Изменяет книгу")
    void update() {
        when(authorService.getByName(anyString())).thenReturn(new Author(1, "yoshimoto banana"));
        when(genreService.getByName(anyString())).thenReturn(new Genre(1, "poem"));

        assertDoesNotThrow(() -> bookService.update(1, "amrita", "poem", "yoshimoto banana"));
    }

    @Test
    @DisplayName("Получает книгу по ID")
    void getById() {
        Book expectedBook = new Book(1L, "tsugumi", new Genre(1L, "poem"), new Author(1L, "yoshimoto banana"));
        when(bookDao.getById(anyLong())).thenReturn(expectedBook);

        Book actualBook = bookService.getById(1L);

        assertEquals(expectedBook, actualBook);
    }

    @Test
    @DisplayName("Получает все книги")
    void getAll() {
        List<Book> expectedBooks = List.of(new Book(1L, "tsugumi", new Genre(1L, "poem"), new Author(1L, "yoshimoto banana")));
        when(bookDao.getAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getAll();

        assertNotEquals(Collections.emptyList(), actualBooks);
        assertEquals(expectedBooks, actualBooks);

    }

    @Test
    @DisplayName("Удаляет книгу по ID")
    void deleteById() {
        assertDoesNotThrow(() -> bookService.deleteById(1));
    }
}