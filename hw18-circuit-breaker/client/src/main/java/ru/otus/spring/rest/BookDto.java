package ru.otus.spring.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    private String name;

    private String genre;

    private String author;

}
