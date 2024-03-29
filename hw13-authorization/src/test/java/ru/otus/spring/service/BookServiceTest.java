package ru.otus.spring.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(BookServiceImpl.class)
@DisplayName("Service для работы с книгами")
class BookServiceTest {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Сохраняет книгу")
    void insert() {
        ObjectId mockedBookId = new ObjectId();
        Book mockedBook = mock(Book.class);
        when(mockedBook.getId()).thenReturn(mockedBookId);
        when(authorService.getByName(anyString())).thenReturn(new Author("yoshimoto banana"));
        when(genreService.getByName(anyString())).thenReturn(new Genre("poem"));
        when(bookRepository.save(any())).thenReturn(mockedBook);

        ObjectId actualId = bookService.save(mockedBookId, "moshi moshi", "poem", "yoshimoto banana");

        assertEquals(mockedBookId, actualId);
    }

    @Test
    @DisplayName("Изменяет книгу")
    void update() {
        when(authorService.getByName(anyString())).thenReturn(new Author("yoshimoto banana"));
        when(genreService.getByName(anyString())).thenReturn(new Genre("poem"));
        when(bookRepository.save(any())).thenReturn(mock(Book.class));

        assertDoesNotThrow(() -> bookService.save(new ObjectId(), "amrita", "poem", "yoshimoto banana"));
    }

    @Test
    @DisplayName("Получает книгу по ID")
    void getById() {
        Book expectedBook = new Book("tsugumi", new Genre("poem"), new Author("yoshimoto banana"));
        when(bookRepository.findById(any())).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.getById(any());

        assertEquals(expectedBook, actualBook);
    }

    @Test
    @DisplayName("Получает все книги")
    void getAll() {
        List<Book> expectedBooks = List.of(new Book("tsugumi", new Genre("poem"), new Author("yoshimoto banana")));
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getAll();

        assertNotEquals(Collections.emptyList(), actualBooks);
        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    @DisplayName("Удаляет книгу по ID")
    void deleteById() {
        ObjectId bookId = new ObjectId();
        Book expectedBook = new Book(bookId, "tsugumi", new Genre("poem"), new Author("yoshimoto banana"));
        when(bookRepository.findById(any())).thenReturn(Optional.of(expectedBook));

        assertDoesNotThrow(() -> bookService.deleteById(bookId));

        verify(bookRepository).deleteById(bookId);
        verify(commentRepository).deleteAllByBookId(bookId);
    }

}