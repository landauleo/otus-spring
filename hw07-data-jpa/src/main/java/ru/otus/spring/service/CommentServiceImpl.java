package ru.otus.spring.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final BookService bookService;

    @Transactional
    public Comment save(long bookId, String text) {
        Book book = bookService.getById(bookId);
        Comment comment = new Comment(text, book);
        return commentDao.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getByBookId(long bookId) {
        return commentDao.findByBookId(bookId);
    }

    @Transactional(readOnly = true)
    public Comment getById(long id) {
        return commentDao.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No comment with id: " + id);});
    }

    @Transactional
    public void deleteById(long id) {
        commentDao.deleteById(id);
    }

}
