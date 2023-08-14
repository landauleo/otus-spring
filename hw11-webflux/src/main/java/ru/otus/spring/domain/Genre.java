package ru.otus.spring.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genre")
public class Genre {

    @Id
    private ObjectId id;

    private String name;

    public Genre(ObjectId id) {
        this.id = id;
    }

    public Genre(String name) {
        this.name = name;
    }
}
