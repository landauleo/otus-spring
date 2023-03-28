package ru.otus.spring.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString(exclude = {"genre", "author"}) //чтобы такого не делать нужно юзать ДТО, но это уже другая история
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "book")
public class Book {

    @Id
    private long id;

    private String name;

    private Genre genre;

    private Author author;

    public Book(long id, String name, Genre genre, Author author) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.author = author;
    }

    public Book(String name, Genre genre, Author author) {
        this.name = name;
        this.genre = genre;
        this.author = author;
    }

}