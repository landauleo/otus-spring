package ru.otus.spring.changelog;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.GenreRepository;

@Component
@RequiredArgsConstructor
public class DatabaseChangelog {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public void insertEntities() {
        authorRepository.saveAll(List.of(new Author("poem"),
                new Author(new ObjectId(),"Stieg Larsson"),
                new Author(new ObjectId(),"J.R.R. Tolkien"),
                new Author(new ObjectId(),"J.K. Rowling"),
                new Author(new ObjectId(),"Douglas Adams"))).subscribe();

        genreRepository.saveAll(List.of(new Genre("poem"),
                new Genre(new ObjectId(),"fable"),
                new Genre(new ObjectId(),"fantasy"),
                new Genre(new ObjectId(),"crime"),
                new Genre(new ObjectId(),"drama"))).subscribe();

    }

}