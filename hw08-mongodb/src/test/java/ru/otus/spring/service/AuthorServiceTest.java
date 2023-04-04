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
import ru.otus.spring.repository.AuthorRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@Import({AuthorServiceImpl.class})
@DisplayName("Service для работы с книгами")
@DataMongoTest
class AuthorServiceTest {

    @Autowired
    AuthorServiceImpl authorServiceImpl;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void testGetByNonExistingName() {
        EmptyResultDataAccessException exception = assertThrows(EmptyResultDataAccessException.class, () -> authorServiceImpl.getByName("not existing name"));
        assertEquals(exception.getMessage(), "No author with name: not existing name");

    }

    @Test
    void testGetByExistingName() {
        String existingAuthorName = "existing name";
        authorRepository.save(new Author(existingAuthorName));

        assertDoesNotThrow(() -> authorServiceImpl.getByName(existingAuthorName));
    }

}