package ru.otus.spring.domain;

import java.util.List;

public class Question {

    private final String text;
    private final List<Option> options;
    private final Option answer;

    public Question(String text, List<Option> options, Option answer) {
        this.text = text;
        this.options = options;
        this.answer = answer;
    }
}
