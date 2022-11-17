package ru.otus.spring.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final GenreServiceImpl genreService;
    private final AuthorServiceImpl authorService;

    public long insert(String bookName, String genreName, String authorName) {
        Book book = Book.builder().name(bookName).genre(genreService.getByName(genreName)).author(authorService.getByName(authorName)).build();
        return bookDao.insert(book);
    }

    public void update(long id, String bookName, String genreName, String authorName) {
        Book book = new Book(id, bookName, genreService.getByName(genreName), authorService.getByName(authorName));
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
