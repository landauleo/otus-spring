package ru.otus.spring.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(CommentServiceImpl.class)
@DisplayName("Service для работы с комментариями")
class CommentServiceImplTest {

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private CommentDao commentDao;

    @Autowired
    private CommentServiceImpl commentService;

    @Test
    void save() {
        long commentId = 1L;
        long bookId = 2L;
        when(bookDao.getById(anyLong())).thenReturn(new Book());
        when(commentDao.save(any(), anyLong())).thenReturn(commentId);
        long savedCommentId = commentService.save(bookId, "blah-blah");

        assertEquals(commentId, savedCommentId);
    }

    @Test
    void getByBookId() {
        Comment comment = new Comment();
        long bookId = 2L;
        when(commentDao.getByBookId(bookId)).thenReturn(List.of(comment));

        List<Comment> commentsByBookId = commentService.getByBookId(bookId);

        assertTrue(commentsByBookId.contains(comment));
    }

    @Test
    void getById() {
        Comment actualComment = new Comment();
        when(commentDao.getById(anyLong())).thenReturn(actualComment);

        Comment expectedComment = commentService.getById(1L);

        assertEquals(expectedComment, actualComment);
    }

    @Test
    void deleteById() {
        assertDoesNotThrow(() -> commentService.deleteById(1));
    }

}