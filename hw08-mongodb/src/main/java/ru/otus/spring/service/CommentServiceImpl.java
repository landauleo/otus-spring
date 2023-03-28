package ru.otus.spring.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Transactional
    public Comment save(long id, long bookId, String text) {
        Book book = bookService.getById(bookId);
        Comment comment = new Comment(id, text, book);

        if (id == 0) {
            Comment topByOrderByIdDesc = commentRepository.findTopByOrderByIdDesc();
            comment = topByOrderByIdDesc == null ? new Comment(text, book) : new Comment(topByOrderByIdDesc.getId() + 1, text, book);
        }

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getByBookId(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Transactional(readOnly = true)
    public Comment getById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new EmptyResultDataAccessException("No comment with id: " + id, 1);
        });
    }

    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

}
