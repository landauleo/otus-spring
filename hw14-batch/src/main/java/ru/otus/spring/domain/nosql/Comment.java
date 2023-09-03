package ru.otus.spring.domain.nosql;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Slf4j
@Getter
@Setter
@ToString(exclude = {"book"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comment")
public class Comment {

    @Id
    private String id;

    private String text;

    @DocumentReference(collection = "book")
    private Book book;

    public static Comment of(ru.otus.spring.domain.sql.Comment comment) {
        log.info("Transferring SQL Comment to NO SQL Comment");
        return new Comment(comment.getId().toString(), comment.getText(), Book.of(comment.getBook()));
    }

}
