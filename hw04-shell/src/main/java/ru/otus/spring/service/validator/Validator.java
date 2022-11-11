package ru.otus.spring.service.validator;

import java.util.List;

public interface Validator {

    void validate(List<String> expected, List<String> actual);
}
