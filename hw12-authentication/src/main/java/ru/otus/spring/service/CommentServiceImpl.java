package ru.otus.spring.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
    public Comment save(ObjectId id, ObjectId bookId, String text) {
        Book book = bookService.getById(bookId);
        Comment comment = id == null ? new Comment(text, book) : new Comment(id, text, book);

        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getByBookId(ObjectId bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Transactional(readOnly = true)
    public Comment getById(ObjectId id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new EmptyResultDataAccessException("No comment with id: " + id, 1);
        });
    }

    @Transactional
    public void deleteById(ObjectId id) {
        commentRepository.deleteById(id);
    }

}
