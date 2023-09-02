package ru.otus.spring.domain.nosql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "author")
public class Author {

    @Id
    private String id;

    private String name;

    public static Author of(ru.otus.spring.domain.sql.Author author) {
        return new Author(author.getId().toString(), author.getName());
    }

}
