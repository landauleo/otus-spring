package ru.otus.spring.domain;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class BookWithComments {
    private String id;
    private String title;
    private List<Author> authors;
    private List<Genre> genres;
    private List<Comment> comments;

}
