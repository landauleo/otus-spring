package ru.otus.spring.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({CommentServiceImpl.class})
@DisplayName("Service для работы с комментариями")
class CommentServiceTest {

    @Autowired
    private CommentServiceImpl commentService;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Сохраняет комментарий")
    void save() {
        Book mockBook = mock(Book.class);
        ObjectId bookId = new ObjectId();
        when(mockBook.getId()).thenReturn(bookId);
        String text = "blah-blah";
        when(bookService.getById(bookId)).thenReturn(mockBook);
        ArgumentCaptor<Comment> ac = ArgumentCaptor.forClass(Comment.class);

        commentService.save(new ObjectId(), bookId, text);

        verify(bookService).getById(bookId);
        verify(commentRepository).save(ac.capture());
        assertEquals(bookId, ac.getValue().getBook().getId());
        assertEquals(text, ac.getValue().getText());
    }

    @Test
    @DisplayName("Получает комментарий по id книги")
    void getByBookId() {
        Comment comment = new Comment();
        ObjectId bookId = new ObjectId();
        when(commentRepository.findByBookId(bookId)).thenReturn(List.of(comment));

        List<Comment> commentsByBookId = commentService.getByBookId(bookId);

        assertTrue(commentsByBookId.contains(comment));
    }

    @Test
    @DisplayName("Получает комментарий по id")
    void getById() {
        Comment actualComment = new Comment();
        when(commentRepository.findById(any())).thenReturn(Optional.of(actualComment));

        Comment expectedComment = commentService.getById(new ObjectId());

        assertEquals(expectedComment, actualComment);
    }

    @Test
    @DisplayName("Удаляет комментарий по id комментария")
    void deleteById() {
        ObjectId bookId = new ObjectId();
        Comment comment = new Comment();
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        assertDoesNotThrow(() -> commentService.deleteById(bookId));

        verify(commentRepository).deleteById(bookId);
    }

}