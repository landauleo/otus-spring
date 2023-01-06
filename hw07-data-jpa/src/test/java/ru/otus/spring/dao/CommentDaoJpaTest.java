package ru.otus.spring.dao;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@DisplayName("Dao для работы с комментариями")
class CommentDaoJpaTest {

    @Autowired
    private CommentDao commentDao;

    @MockBean
    private BookDao bookDao;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Сохраняет комментарий")
    void testInsert() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        long bookId = testEntityManager.persistAndFlush(book).getId();

        assertEquals(0, commentDao.getByBookId(bookId).size());

        when(bookDao.findById(bookId)).thenReturn(Optional.of(book));
        Comment comment = new Comment("filthy animals", book);
        commentDao.save(comment);
        assertEquals(1, commentDao.getByBookId(bookId).size());
    }

    @Test
    @DisplayName("Изменяет комментарий")
    void testUpdate() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        Comment comment = new Comment("filthy animals", book);

        testEntityManager.persistAndFlush(book);
        testEntityManager.persistAndFlush(comment);
        long commentId = comment.getId();
        Comment originalCommentFromDb = commentDao.getById(commentId);
        testEntityManager.detach(originalCommentFromDb);

        Comment updatedVersionOfComment = new Comment(commentId, "fluffy animals");
        commentDao.save(updatedVersionOfComment);
        Comment updatedCommentFromDb = testEntityManager.find(Comment.class, commentId);

        assertEquals(comment, originalCommentFromDb);
        assertNotEquals(originalCommentFromDb, updatedCommentFromDb);
    }

    @Test
    @DisplayName("Получает комментарии по ID книги")
    void testGetById() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        Comment negativeComment = new Comment("I hate japanese literature", book);
        Comment positiveComment = new Comment("I love japanese literature", book);

        long bookId = testEntityManager.persistAndFlush(book).getId();
        testEntityManager.persistAndFlush(negativeComment);
        testEntityManager.clear();
        testEntityManager.persistAndFlush(positiveComment);
        testEntityManager.clear();
        int originalSize = commentDao.getByBookId(bookId).size();

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

        long bookId = testEntityManager.persistAndFlush(book).getId();
        long commentId = testEntityManager.persistAndFlush(comment).getId();
        testEntityManager.clear();
        Comment foundComment = testEntityManager.find(Comment.class, commentId);
        assertThat(foundComment).isNotNull();

        commentDao.deleteById(commentId);
        testEntityManager.flush();
        Comment deletedComment = testEntityManager.find(Comment.class, commentId);
        Book notDeletedBook = testEntityManager.find(Book.class, bookId);

        assertThat(deletedComment).isNull();
        assertNotNull(notDeletedBook);
    }

}