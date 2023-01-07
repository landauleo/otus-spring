package ru.otus.spring.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Transactional
    public Comment save(long bookId, String text) {
        Book book = bookService.getById(bookId);
        Comment comment = new Comment(text, book);
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getByBookId(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Transactional(readOnly = true)
    public Comment getById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No comment with id: " + id);});
    }

    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

}
