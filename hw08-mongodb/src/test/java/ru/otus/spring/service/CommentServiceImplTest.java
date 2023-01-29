package ru.otus.spring.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({CommentServiceImpl.class})
@DisplayName("Service для работы с комментариями")
class CommentServiceImplTest {


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
    void save() {
        Book mockBook = mock(Book.class);
        long bookId = 2L;
        when(mockBook.getId()).thenReturn(bookId);
        String text = "blah-blah";
        when(bookService.getById(bookId)).thenReturn(mockBook);
        ArgumentCaptor<Comment> ac = ArgumentCaptor.forClass(Comment.class);

        commentService.save(bookId, text);

        verify(bookService).getById(bookId);
        verify(commentRepository).save(ac.capture());
        assertEquals(bookId, ac.getValue().getBook().getId());
        assertEquals(text, ac.getValue().getText());
    }

    @Test
    void getByBookId() {
        Comment comment = new Comment();
        long bookId = 2L;
        when(commentRepository.findByBookId(bookId)).thenReturn(List.of(comment));

        List<Comment> commentsByBookId = commentService.getByBookId(bookId);

        assertTrue(commentsByBookId.contains(comment));
    }

    @Test
    void getById() {
        Comment actualComment = new Comment();
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(actualComment));

        Comment expectedComment = commentService.getById(1L);

        assertEquals(expectedComment, actualComment);
    }

    @Test
    void deleteById() {
        assertDoesNotThrow(() -> commentService.deleteById(1));
    }

}