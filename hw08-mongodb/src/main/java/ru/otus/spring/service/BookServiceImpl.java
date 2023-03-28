package ru.otus.spring.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Transactional
    public long save(long id, String bookName, String genreName, String authorName) {
        Genre genre = genreService.getByName(genreName);
        Author author = authorService.getByName(authorName);
        Book book = new Book(id, bookName, genre, author);

        //так неудобно, потому что в документах mongodb нет автоинкремента для Id типа long
        if (id == 0) {
            Book topByOrderByIdDesc = bookRepository.findTopByOrderByIdDesc();
            book = topByOrderByIdDesc == null ? new Book(bookName, genre, author) : new Book(topByOrderByIdDesc.getId() + 1, bookName, genre, author);
        }

        return bookRepository.save(book).getId();
    }

    @Transactional(readOnly = true)
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> {
            throw new EmptyResultDataAccessException("No book with id: " + id, 1);
        });
    }

    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

}
