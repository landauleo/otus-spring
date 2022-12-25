package ru.otus.spring.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.domain.Comment;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Transactional
    public long save(long bookId, String text) {
        Comment comment = new Comment(text);
        return commentDao.save(comment, bookId);
    }

    @Transactional(readOnly = true)
    public List<Comment> getByBookId(long bookId) {
        return commentDao.getByBookId(bookId);
    }

    @Transactional(readOnly = true)
    public Comment getById(long id) {
        return commentDao.getById(id);
    }

    @Transactional
    public void deleteById(long id) {
        commentDao.deleteById(id);
    }

}
