package ru.otus.spring.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString(exclude = {"book"})
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection = "comment")
public class Comment {

    @Id
    private long id;

    private String text;

    private Book book;

    public Comment(long id, String text, Book book) {
        this.id = id;
        this.text = text;
        this.book = book;
    }

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

    public Comment(long id, String text) {
        this.id = id;
        this.text = text;
    }

}
