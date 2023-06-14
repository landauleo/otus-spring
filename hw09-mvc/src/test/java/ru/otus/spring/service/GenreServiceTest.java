package ru.otus.spring.service;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.repository.GenreRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        String nonExistingGenreName = "not existing name";

        assertEquals(Optional.empty(), genreRepository.findByName(nonExistingGenreName));
        assertDoesNotThrow(() -> genreServiceImpl.getByName(nonExistingGenreName));
        assertNotNull(genreRepository.findByName(nonExistingGenreName));

    }

    @Test
    void testGetByExistingName() {
        String existingGenreName = "existing name";

        assertEquals(Optional.empty(), genreRepository.findByName(existingGenreName));
        assertDoesNotThrow(() -> genreServiceImpl.getByName(existingGenreName));
        assertNotNull(genreRepository.findByName(existingGenreName));
    }

}