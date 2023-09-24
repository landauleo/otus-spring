package ru.otus.spring.rest;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book", url = "localhost:8666/")
public interface BookClient {

    @GetMapping("/api/book")
    List<BookDto> getAllBooks();

    @DeleteMapping("/api/book/{id}")
    void deleteBook(@PathVariable("id") String id);

    @PostMapping(value = "/api/book")
    String saveBook(@RequestBody BookDto bookDto);

}
