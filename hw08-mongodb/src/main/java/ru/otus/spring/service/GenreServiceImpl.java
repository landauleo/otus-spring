package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public Genre getByName(String name) {
        return genreRepository.findByName(name).orElseThrow(() -> {
            throw new EmptyResultDataAccessException("No genre with name: " + name, 1);
        });
    }

}
