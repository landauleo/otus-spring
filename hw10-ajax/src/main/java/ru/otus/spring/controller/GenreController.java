package ru.otus.spring.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.GenreService;

@Slf4j
@RestController//вместо @Controller говорит SpringBoot, что больше не надо искать View
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public List<String > getAllGenres() {
        List<String> genreNames = genreService.getAll()
                .stream()
                .map(Genre::getName).toList();
        log.info("{} genres found", genreNames.size());
        return genreNames;
    }
}
