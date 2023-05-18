package ru.otus.spring.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    private String name;

    private String genre;

    private String author;

    public static BookDto toDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor().getName());
        dto.setGenre(entity.getGenre().getName());
        return dto;
    }

}
