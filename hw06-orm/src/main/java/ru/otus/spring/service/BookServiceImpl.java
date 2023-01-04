package ru.otus.spring.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Transactional
    public long save(long id, String bookName, String genreName, String authorName) {
        Genre genre = genreService.getByName(genreName);
        Author author = authorService.getByName(authorName);

        Book book = new Book(id, bookName, genre, author);
        return bookDao.save(book);
    }

    @Transactional(readOnly = true)
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Transactional
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

}
