package ru.otus.spring.shell;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.BookService;

@ShellComponent
@ShellCommandGroup("Book commands")
@RequiredArgsConstructor
public class BookCommand {

    private final BookService bookService;

    @ShellMethod(value = "Insert a new book", key = {"ib", "insertbook"})
    public long insert(@ShellOption(defaultValue = "aku no hana") String bookName,
                       @ShellOption(defaultValue = "poetry") String genreName,
                       @ShellOption(defaultValue = "bodler") String authorName) {
        return bookService.save(0, bookName, genreName, authorName);
    }

    @ShellMethod(value = "Update a book", key = {"ub", "updatebook"})
    public void update(@ShellOption(defaultValue = "1") long id,
                       @ShellOption(defaultValue = "albatross") String bookName,
                       @ShellOption(defaultValue = "poetry") String genreName,
                       @ShellOption(defaultValue = "bodler") String authorName) {
        bookService.save(id, bookName, genreName, authorName);
    }

    @ShellMethod(value = "Get a book by id", key = {"gb", "getbook"})
    public Book getById(@ShellOption(defaultValue = "1") long id) {
        return bookService.getById(id);
    }

    @ShellMethod(value = "Get all books", key = {"gab", "getallbooks"})
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @ShellMethod(value = "Delete a book by id", key = {"db", "deletebook"})
    public void deleteById(@ShellOption(defaultValue = "1") long id) {
        bookService.deleteById(id);
    }

}
