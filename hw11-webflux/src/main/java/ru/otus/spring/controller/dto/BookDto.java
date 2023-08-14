package ru.otus.spring.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import ru.otus.spring.domain.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;

    private String name;

    private GenreDto genre;

    private AuthorDto author;

    public static BookDto toDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId() == null ? new ObjectId().toString() : entity.getId().toString());
        dto.setName(entity.getName());
        dto.setAuthor(AuthorDto.toDto(entity.getAuthor()));
        dto.setGenre(GenreDto.toDto(entity.getGenre()));
        return dto;
    }

}
