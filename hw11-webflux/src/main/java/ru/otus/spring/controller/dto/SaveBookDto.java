package ru.otus.spring.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveBookDto {

    private String name;

    private String genreId;

    private String authorId;
}
