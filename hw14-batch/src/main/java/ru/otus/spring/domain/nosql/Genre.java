package ru.otus.spring.domain.nosql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genre")
public class Genre {

    @Id
    private String id;

    private String name;


    public static Genre of(ru.otus.spring.domain.sql.Genre genre) {
        log.info("Transferring SQL Genre to NO SQL Genre");
        return new Genre(genre.getId().toString(), genre.getName());
    }

}
