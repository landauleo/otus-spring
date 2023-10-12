package ru.otus.spring.rest;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.service.BookService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/book")
    public List<BookDto> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        log.info("CLIENT: {} books found", books.size());
        return books;
    }

    @DeleteMapping("/api/book/{id}")
    public void delete(@PathVariable("id") String id) {
        bookService.deleteBook(id);
        log.info("CLIENT: book with id {} was deleted", id);
    }

    @PostMapping(value = "/api/book")
    public void saveBook(@RequestBody BookDto bookDto) {
        String id = bookService.saveBook(bookDto);
        log.info("CLIENT: book with id {} was saved ", id);
    }

}
