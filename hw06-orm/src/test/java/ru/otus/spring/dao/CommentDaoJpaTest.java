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
        Genre genre = new Genre( "poem");
        Author author = new Author( "yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        Comment comment = new Comment( "filthy animals", book);

        testEntityManager.persistAndFlush(book);
        long commentId = commentDaoJpa.save(comment);
        Comment commentFromDb = testEntityManager.find(Comment.class, commentId);

        assertNotNull(commentFromDb);
        assertEquals(comment, commentFromDb);
    }

    @Test
    @DisplayName("Изменяет комментарий")
    void testUpdate() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        Comment comment = new Comment("filthy animals", book);

        testEntityManager.persistAndFlush(book);
        long commentId = commentDaoJpa.save(comment);
        Comment originalCommentFromDb = testEntityManager.find(Comment.class, commentId);
        testEntityManager.detach(originalCommentFromDb);

        Comment updatedVersionOfComment = new Comment(commentId, "fluffy animals", book);
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
        int originalSize = commentDaoJpa.getByBookId(bookId).size();
        testEntityManager.persistAndFlush(negativeComment);
        testEntityManager.persistAndFlush(positiveComment);

        int updatedSize = commentDaoJpa.getByBookId(bookId).size();

        assertNotEquals(originalSize, updatedSize);
    }


    @Test
    @DisplayName("Удаляет комментарий по ID")
    void testDeleteById() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        Comment comment = new Comment("filthy animals", book);

        testEntityManager.persistAndFlush(book);
        long id = testEntityManager.persistAndFlush(comment).getId();
        Comment foundComment = testEntityManager.find(Comment.class, id);
        assertThat(foundComment).isNotNull();
        testEntityManager.detach(foundComment);

        commentDaoJpa.deleteById(id);
        Comment deletedComment = testEntityManager.find(Comment.class, id);

        assertThat(deletedComment).isNull();
    }

}