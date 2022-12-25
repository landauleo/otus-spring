package ru.otus.spring.dao;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@DataJpaTest
@Import(CommentDaoJpa.class)
@DisplayName("Dao для работы с комментариями")
class CommentDaoJpaTest {

    @Autowired
    private CommentDaoJpa commentDaoJpa;

    @MockBean
    private BookDaoJpa bookDaoJpa;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Сохраняет комментарий")
    void testInsert() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        long bookId = testEntityManager.persistAndFlush(book).getId();

        assertEquals(0, commentDaoJpa.getByBookId(bookId).size());

        when(bookDaoJpa.getById(bookId)).thenReturn(book);
        Comment comment = new Comment("filthy animals");
        commentDaoJpa.save(comment, bookId);
        assertEquals(1, commentDaoJpa.getByBookId(bookId).size());
    }

    @Test
    @DisplayName("Изменяет комментарий")
    void testUpdate() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Comment comment = new Comment("filthy animals");
        Book book = new Book("sleeping", genre, author, List.of(comment));

        testEntityManager.persistAndFlush(book);
        long commentId = comment.getId();
        Comment originalCommentFromDb = commentDaoJpa.getById(commentId);
        testEntityManager.detach(originalCommentFromDb);

        Comment updatedVersionOfComment = new Comment(commentId, "fluffy animals");
        commentDaoJpa.save(updatedVersionOfComment, book.getId());
        Comment updatedCommentFromDb = testEntityManager.find(Comment.class, commentId);

        assertEquals(comment, originalCommentFromDb);
        assertNotEquals(originalCommentFromDb, updatedCommentFromDb);
    }

    @Test
    @DisplayName("Получает комментарии по ID книги")
    void testGetById() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Comment negativeComment = new Comment("I hate japanese literature");
        Comment positiveComment = new Comment("I love japanese literature");
        Book book = new Book("sleeping", genre, author, List.of(positiveComment, negativeComment));

        long bookId = testEntityManager.persistAndFlush(book).getId();
        int originalSize = commentDaoJpa.getByBookId(bookId).size();
        commentDaoJpa.getByBookId(bookId);

        assertNotEquals(0, originalSize);
        assertEquals(2, originalSize);
    }

    @Test
    @DisplayName("Удаляет комментарий по ID")
    void testDeleteById() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Comment comment = new Comment("filthy animals");
        Book book = new Book("sleeping", genre, author, List.of(comment));

        long bookId = testEntityManager.persistAndFlush(book).getId();
        long commentId = comment.getId();
        Comment foundComment = testEntityManager.find(Comment.class, commentId);
        assertThat(foundComment).isNotNull();

        commentDaoJpa.deleteById(commentId);
        Comment deletedComment = testEntityManager.find(Comment.class, commentId);
        Book notDeletedBook = testEntityManager.find(Book.class, bookId);

        assertThat(deletedComment).isNull();
        assertNotNull(notDeletedBook);
    }

}