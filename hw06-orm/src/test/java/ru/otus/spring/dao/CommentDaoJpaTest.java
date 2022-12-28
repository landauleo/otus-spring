package ru.otus.spring.dao;

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
        Comment comment = new Comment("filthy animals", book);
        commentDaoJpa.save(comment);
        assertEquals(1, commentDaoJpa.getByBookId(bookId).size());
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
        Comment originalCommentFromDb = commentDaoJpa.getById(commentId);
        testEntityManager.detach(originalCommentFromDb);

        Comment updatedVersionOfComment = new Comment(commentId, "fluffy animals");
        commentDaoJpa.save(updatedVersionOfComment);
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
        int originalSize = commentDaoJpa.getByBookId(bookId).size();

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
        testEntityManager.clear();//засранец брал сущность из кэша, не селектил из БД и обманывал меня
        Comment foundComment = testEntityManager.find(Comment.class, commentId);
        assertThat(foundComment).isNotNull();

        commentDaoJpa.deleteById(commentId);
        testEntityManager.flush();
        Comment deletedComment = testEntityManager.find(Comment.class, commentId);
        Book notDeletedBook = testEntityManager.find(Book.class, bookId);

        assertThat(deletedComment).isNull();
        assertNotNull(notDeletedBook);
    }

}