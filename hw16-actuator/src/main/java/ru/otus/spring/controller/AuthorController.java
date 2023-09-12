package ru.otus.spring.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.domain.Author;
import ru.otus.spring.service.AuthorService;

@Slf4j
@RestController//вместо @Controller говорит SpringBoot, что больше не надо искать View
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/author")
    public List<String> getAllAuthors() {
        List<String> authorNames = authorService.getAll()
                .stream()
                .map(Author::getName).toList();
        log.info("{} authorNames found", authorNames.size());
        return authorNames;
    }

}
