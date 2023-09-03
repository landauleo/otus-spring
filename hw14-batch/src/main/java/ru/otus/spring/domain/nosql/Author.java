package ru.otus.spring.domain.nosql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "author")
public class Author {

    @Id
    private String id;

    private String name;

    public static Author of(ru.otus.spring.domain.sql.Author author) {
        log.info("Transferring SQL Author to NO SQL Author");
        return new Author(author.getId().toString(), author.getName());
    }

}
