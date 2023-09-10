package ru.otus.spring.service;

import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({BookServiceImpl.class, CommentServiceImpl.class})
@DisplayName("Service для работы с книгами")
@DataMongoTest
class BookServiceAndRepositoryTest {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentServiceImpl commentService;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @Test
    @DisplayName("Изменяет книгу и её изменения видны в сущности комментария")
    void update() {
        ObjectId bookId = new ObjectId();
        ObjectId commentId = new ObjectId();
        String originalBookName = "tsugumi";
        String genreName = "poem";
        String authorName = "yoshimoto banana";
        when(authorService.getByName(anyString())).thenReturn(new Author("yoshimoto banana"));
        when(genreService.getByName(anyString())).thenReturn(new Genre("poem"));

        bookService.save(bookId, originalBookName, genreName, authorName);
        commentService.save(commentId, bookId, "haha, not funny");
        List<Comment> commentsByBookId = commentService.getByBookId(bookId);

        assertNotEquals(Collections.emptyList(), commentsByBookId);
        assertEquals(originalBookName, commentsByBookId.get(0).getBook().getName());

        String updatedBookName = "amrita";
        bookService.save(bookId, updatedBookName, genreName, authorName);
        commentsByBookId = commentService.getByBookId(bookId);

        assertEquals(updatedBookName, commentsByBookId.get(0).getBook().getName());
    }


    @Test
    @DisplayName("Удаляет книгу и каскадно удаляет комментарии")
    void deleteById() {
        ObjectId bookId = new ObjectId();
        ObjectId commentId = new ObjectId();
        String bookName = "tsugumi";
        String genreName = "poem";
        String authorName = "yoshimoto banana";
        when(authorService.getByName(anyString())).thenReturn(new Author("yoshimoto banana"));
        when(genreService.getByName(anyString())).thenReturn(new Genre("poem"));

        bookService.save(bookId, bookName, genreName, authorName);
        commentService.save(commentId, bookId, "haha, not funny");
        List<Comment> commentsByBookId = commentService.getByBookId(bookId);

        assertEquals(1, commentsByBookId.size());

        bookService.deleteById(bookId);
        commentsByBookId = commentService.getByBookId(bookId);

        assertEquals(0, commentsByBookId.size());
    }

}