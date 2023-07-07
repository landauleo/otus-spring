package ru.otus.spring.changelog;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

@Component
@RequiredArgsConstructor
public class DatabaseChangelog {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public void clearDb() {
        bookRepository.deleteAll().subscribe();
        authorRepository.deleteAll().subscribe();
        genreRepository.deleteAll().subscribe();
    }

    public void insertEntities() {
        Genre poem = new Genre("poem");
        Genre fable = new Genre("fable");
        Genre fantasy = new Genre("fantasy");
        Genre crime = new Genre("crime");
        Genre drama = new Genre("drama");
        genreRepository.saveAll(List.of(poem, fable, fantasy, crime, drama)).collectList().block();

        Author larsson = new Author("Stieg Larsson");
        Author tolkien = new Author("J.R.R. Tolkien");
        Author adams = new Author("Douglas Adams");
        Author yoshimoto = new Author("Banana Yoshimoto");
        authorRepository.saveAll(List.of(larsson, tolkien, adams, yoshimoto)).collectList().block();

        Book sleeping = new Book("sleeping", poem, yoshimoto);
        Book amrita = new Book("Amrita", poem, yoshimoto);
        Book np = new Book("N.P.", poem, yoshimoto);
        bookRepository.saveAll(List.of(sleeping, amrita, np)).collectList().block();
    }

}