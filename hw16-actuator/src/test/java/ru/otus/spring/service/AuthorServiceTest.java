package ru.otus.spring.service;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.repository.AuthorRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@Import({AuthorServiceImpl.class})
@DisplayName("Service для работы с книгами")
@DataMongoTest
class AuthorServiceTest {

    @Autowired
    private AuthorServiceImpl authorServiceImpl;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void testGetByNonExistingName() {
        String nonExistingGenreName = "not existing name";

        assertEquals(Optional.empty(), authorRepository.findByName(nonExistingGenreName));
        assertDoesNotThrow(() -> authorServiceImpl.getByName(nonExistingGenreName));
        assertNotNull(authorServiceImpl.getByName(nonExistingGenreName));
    }

    @Test
    void testGetByExistingName() {
        String existingGenreName = "existing name";

        assertEquals(Optional.empty(), authorRepository.findByName(existingGenreName));
        assertDoesNotThrow(() -> authorServiceImpl.getByName(existingGenreName));
        assertNotNull(authorRepository.findByName(existingGenreName));
    }

}