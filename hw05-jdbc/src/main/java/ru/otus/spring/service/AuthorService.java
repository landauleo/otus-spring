package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorDao authorDao;

    public Author getByName(String name) {
        return authorDao.getByName(name);
    }

}
