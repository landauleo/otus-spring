package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public Author getByName(String name) {
        return authorRepository.findByName(name).orElseThrow(() -> {
            throw new EmptyResultDataAccessException("No author with name: " + name, 1);
        });
    }

}
