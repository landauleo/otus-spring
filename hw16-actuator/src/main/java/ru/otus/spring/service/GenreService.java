package ru.otus.spring.service;

import java.util.List;

import ru.otus.spring.domain.Genre;

public interface GenreService {

    Genre getByName(String name);

    List<Genre> getAll();

}
