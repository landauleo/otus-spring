package ru.otus.spring.repository;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataMongoTest
@DisplayName("Dao для работы с комментариями")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommentDaoJpaTest {

    @Autowired
    private CommentRepository commentRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Сохраняет комментарий и находит его по id книги")
    void testInsert() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        ObjectId bookId = new ObjectId();
        Book book = new Book(bookId, "sleeping", genre, author);

        assertEquals(0, commentRepository.findByBookId(bookId).size());

        Comment comment = new Comment("filthy animals", book);
        commentRepository.save(comment);
        assertEquals(1, commentRepository.findByBookId(bookId).size());
    }

    @Test
    @DisplayName("Сохраняет много комментариев и находит их по id книги")
    void testInsertMultiple() {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        ObjectId bookId = new ObjectId();
        Book book = new Book(bookId, "sleeping", genre, author);

        assertEquals(0, commentRepository.findByBookId(bookId).size());

        Comment firstComment = new Comment("filthy animals", book);
        Comment secondComment = new Comment("fluffy animals", book);
        assertDoesNotThrow(() -> commentRepository.save(firstComment));
        assertDoesNotThrow(() -> commentRepository.save(secondComment));

        assertEquals(2, commentRepository.findByBookId(bookId).size());
    }

    @Test
    @DisplayName("Изменяет комментарий")
    void testUpdate(@Autowired MongoTemplate mongoTemplate) {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        ObjectId commentId = new ObjectId();
        Comment comment = new Comment(commentId, "filthy animals", book);

        commentRepository.save(comment);
        Comment originalCommentFromDb = mongoTemplate.findById(commentId, Comment.class);

        Comment updatedVersionOfComment = new Comment(commentId, "fluffy animals");
        commentRepository.save(updatedVersionOfComment);
        Comment updatedCommentFromDb = mongoTemplate.findById(commentId, Comment.class);

        assertEquals(comment, originalCommentFromDb);
        assertNotEquals(originalCommentFromDb, updatedCommentFromDb);
    }

    @Test
    @DisplayName("Получает комментарии по ID книги")
    void testGetById(@Autowired MongoTemplate mongoTemplate) {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        ObjectId bookId = new ObjectId();
        Book book = new Book(bookId, "sleeping", genre, author);
        Comment negativeComment = new Comment("I hate japanese literature", book);
        Comment positiveComment = new Comment("I love japanese literature", book);

        mongoTemplate.save(negativeComment);
        mongoTemplate.save(positiveComment);
        int originalSize = commentRepository.findByBookId(bookId).size();

        assertNotEquals(0, originalSize);
        assertEquals(2, originalSize);
    }

    @Test
    @DisplayName("Удаляет комментарий по ID")
    void testDeleteById(@Autowired MongoTemplate mongoTemplate) {
        Genre genre = new Genre("poem");
        Author author = new Author("yoshimoto banana");
        Book book = new Book("sleeping", genre, author);
        Comment comment = new Comment("filthy animals", book);
        ObjectId commentId = mongoTemplate.save(comment).getId();
        Comment foundComment = mongoTemplate.findById(commentId, Comment.class);

        commentRepository.deleteById(commentId);

        assertThat(foundComment).isNotNull();
        assertNull(mongoTemplate.findById(commentId, Comment.class));
    }

}