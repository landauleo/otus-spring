package ru.otus.spring.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.controller.dto.BookDto;
import ru.otus.spring.service.BookService;

@Slf4j
@RestController//вместо @Controller говорит SpringBoot, что больше не надо искать View
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    public List<BookDto> getAllBooks() {
        List<BookDto> books = bookService.getAll()
                .stream()
                .map(BookDto::toDto).toList();
        log.info("{} books found", books.size());
        return books;
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable("id") String id) {
        bookService.deleteById(new ObjectId(id));
    }

    @PostMapping(value = "/book")
    public void saveBook(@RequestBody BookDto bookDto) {
        bookService.save(bookDto.getId() == null ? new ObjectId() : new ObjectId(bookDto.getId()),
                bookDto.getName(), bookDto.getGenre(), bookDto.getAuthor());
    }

}
