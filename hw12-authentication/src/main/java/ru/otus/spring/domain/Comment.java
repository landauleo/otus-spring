package ru.otus.spring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
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
    private ObjectId id;

    private String text;

    private Book book;

    public Comment(ObjectId id, String text, Book book) {
        this.id = id;
        this.text = text;
        this.book = book;
    }

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

    public Comment(ObjectId id, String text) {
        this.id = id;
        this.text = text;
    }

}
