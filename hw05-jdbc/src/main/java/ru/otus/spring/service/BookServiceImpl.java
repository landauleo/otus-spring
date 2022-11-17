package ru.otus.spring.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final GenreServiceImpl genreService;
    private final AuthorServiceImpl authorService;

    public long insert(String bookName, String genreName, String authorName) {
        Genre genre = genreService.getByName(genreName);
        Author author = authorService.getByName(authorName);

        Book book = new Book(bookName, genre,author );
        return bookDao.insert(book);
    }

    public void update(long id, String bookName, String genreName, String authorName) {
        Genre genre = genreService.getByName(genreName);
        Author author = authorService.getByName(authorName);

        Book book = new Book(id, bookName, genre, author);
        bookDao.update(book);
    }

    public Book getById(long id) {
        return bookDao.getById(id);
    }

    public List<Book> getAll() {
        return bookDao.getAll();
    }

    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

}
