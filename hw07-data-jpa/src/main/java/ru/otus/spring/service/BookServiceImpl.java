package ru.otus.spring.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

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
        return bookRepository.save(book).getId();
    }

    @Transactional(readOnly = true)
    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> {throw new EntityNotFoundException("No book with id: " + id);});
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
