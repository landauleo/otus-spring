package ru.otus.spring.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Transactional
    public ObjectId save(ObjectId id, String bookName, String genreName, String authorName) {
        Genre genre = genreService.getByName(genreName);
        Author author = authorService.getByName(authorName);
        Book book = id == null ? new Book(bookName, genre, author) : new Book(id, bookName, genre, author);

        ObjectId bookId = bookRepository.save(book).getId();
        for (Comment comment : commentRepository.findByBookId(id)) {
            comment.setBook(book);
            commentRepository.save(comment);
        }

        return bookId;
    }

    @Transactional(readOnly = true)
    public Book getById(ObjectId id) {
        return bookRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("No book with id: " + id, 1));
    }

    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void deleteById(ObjectId id) {
        bookRepository.deleteById(id);
        commentRepository.deleteAllByBookId(id);
    }

}
