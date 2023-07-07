package ru.otus.spring.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import ru.otus.spring.domain.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {

    private String id;

    private String name;

    public static GenreDto toDto(Genre entity) {
        GenreDto dto = new GenreDto();
        dto.setId(entity.getId() == null ? new ObjectId().toString() : entity.getId().toString());
        dto.setName(entity.getName());
        return dto;
    }

}
