package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.BookServiceImpl;

import java.util.List;

@ShellComponent
@ShellCommandGroup("Book commands")
@RequiredArgsConstructor
public class BookCommand {

    private final BookService bookService;

    @ShellMethod(value = "Insert a new book", key = {"i", "insert"})
    public long insert(@ShellOption(defaultValue = "aku no hana") String bookName,
                       @ShellOption(defaultValue = "poetry") String genreName,
                       @ShellOption(defaultValue = "bodler") String authorName) {
        return bookService.insert(bookName, genreName, authorName);
    }

    @ShellMethod(value = "Update a book", key = {"u", "update"})
    public void update(@ShellOption(defaultValue = "1") long id,
                       @ShellOption(defaultValue = "aku no hana") String bookName,
                       @ShellOption(defaultValue = "poetry") String genreName,
                       @ShellOption(defaultValue = "bodler") String authorName) {
        bookService.update(id, bookName, genreName, authorName);
    }

    @ShellMethod(value = "Get a book by id", key = {"g", "get"})
    public Book getById(@ShellOption(defaultValue = "1") long id) {
        return bookService.getById(id);
    }

    @ShellMethod(value = "Get all books", key = {"ga", "getall"})
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @ShellMethod(value = "Delete a book by id", key = {"d", "delete"})
    public void deleteById(@ShellOption(defaultValue = "1") long id) {
        bookService.deleteById(id);
    }

}
