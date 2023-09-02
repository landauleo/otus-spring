package ru.otus.spring.domain.nosql;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {

    @Id
    private String id;

    private String name;

    @DocumentReference(collection = "genre")
    private Genre genre;

    @DocumentReference(collection = "author")
    private Author author;


    public static Book of(ru.otus.spring.domain.sql.Book book) {
        return new Book(book.getId().toString(), book.getName(), Genre.of(book.getGenre()), Author.of(book.getAuthor()));
    }
}
