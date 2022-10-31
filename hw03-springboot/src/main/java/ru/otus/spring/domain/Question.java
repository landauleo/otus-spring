package ru.otus.spring.domain;

import java.util.List;
import java.util.Objects;

public class Question {

    private final String text;
    private final List<Option> options;

    public Question(String text, List<Option> options) {
        this.text = text;
        this.options = options;
    }

    public String getText() {
        return text;
    }

    public List<Option> getOptions() {
        return options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return Objects.equals(text, question.text) && Objects.equals(options, question.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, options);
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", options=" + options +
                '}';
    }

}
