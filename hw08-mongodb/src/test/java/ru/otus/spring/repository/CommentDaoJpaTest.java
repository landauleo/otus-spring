package ru.otus.spring.repository;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DataMongoTest
@DisplayName("Dao для работы с комментариями")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommentDaoJpaTest {

    @Autowired
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Сохраняет комментарий")
    void testInsert() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        when(bookRepository.save(book)).thenReturn(book);
        long bookId = bookRepository.save(book).getId();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        assertEquals(0, commentRepository.findByBookId(bookId).size());

        Comment comment = new Comment("filthy animals", book);
        commentRepository.save(comment);
        assertEquals(1, commentRepository.findByBookId(bookId).size());
    }

    @Test
    @DisplayName("Изменяет комментарий")
    void testUpdate() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        long commentId = 1;
        Comment comment = new Comment(commentId, "filthy animals", book);

        bookRepository.save(book);
        commentRepository.save(comment);
        Comment originalCommentFromDb = commentRepository.getById(commentId);

        Comment updatedVersionOfComment = new Comment(commentId, "fluffy animals");
        commentRepository.save(updatedVersionOfComment);
        Comment updatedCommentFromDb = commentRepository.findById(commentId).get();

        assertEquals(comment, originalCommentFromDb);
        assertNotEquals(originalCommentFromDb, updatedCommentFromDb);
    }

    @Test
    @DisplayName("Получает комментарии по ID книги")
    void testGetById() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        Comment negativeComment = new Comment(1, "I hate japanese literature", book);
        Comment positiveComment = new Comment(2, "I love japanese literature", book);
        when(bookRepository.save(book)).thenReturn(book);

        long bookId = bookRepository.save(book).getId();
        commentRepository.save(negativeComment);
        commentRepository.save(positiveComment);
        int originalSize = commentRepository.findByBookId(bookId).size();

        assertNotEquals(0, originalSize);
        assertEquals(2, originalSize);
    }

    @Test
    @DisplayName("Удаляет комментарий по ID")
    void testDeleteById() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        Comment comment = new Comment("filthy animals", book);
        long commentId = commentRepository.save(comment).getId();
        Comment foundComment = commentRepository.findById(commentId).get();

        commentRepository.deleteById(commentId);

        assertThat(foundComment).isNotNull();
        assertThrows(NoSuchElementException.class, () -> commentRepository.findById(commentId).get());
    }

}