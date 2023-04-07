package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.GenreRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@Import({GenreServiceImpl.class})
@DisplayName("Service для работы с книгами")
@DataMongoTest
class GenreServiceTest {

    @Autowired
    private GenreServiceImpl genreServiceImpl;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void testGetByNonExistingName() {
        EmptyResultDataAccessException exception = assertThrows(EmptyResultDataAccessException.class, () -> genreServiceImpl.getByName("not existing name"));
        assertEquals(exception.getMessage(), "No genre with name: not existing name");

    }

    @Test
    void testGetByExistingName() {
        String existingGenreName = "existing name";
        genreRepository.save(new Genre(existingGenreName));

        assertDoesNotThrow(() -> genreServiceImpl.getByName(existingGenreName));
    }

}