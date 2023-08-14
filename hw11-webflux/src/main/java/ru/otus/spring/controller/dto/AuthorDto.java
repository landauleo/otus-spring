package ru.otus.spring.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import ru.otus.spring.domain.Author;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private String id;

    private String name;

    public static AuthorDto toDto(Author entity) {
        AuthorDto dto = new AuthorDto();
        dto.setId(entity.getId() == null ? new ObjectId().toString() : entity.getId().toString());
        dto.setName(entity.getName());
        return dto;
    }

}
