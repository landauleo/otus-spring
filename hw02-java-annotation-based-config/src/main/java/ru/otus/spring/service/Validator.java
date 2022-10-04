package ru.otus.spring.service;

import ru.otus.spring.domain.Option;

import java.util.List;

public interface Validator {

    void validate(List<Option> expected, List<Option> actual);
}
