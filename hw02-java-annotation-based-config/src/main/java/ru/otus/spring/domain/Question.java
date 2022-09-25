package ru.otus.spring.domain;

import java.util.List;

public class Question {

    private final QuestionText text;
    private final List<Option> options;
    private final Option answer;

    public Question(QuestionText text, List<Option> options, Option answer) {
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public QuestionText getText() {
        return text;
    }

    public List<Option> getOptions() {
        return options;
    }

    public Option getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text=" + text +
                ", options=" + options +
                ", answer=" + answer +
                '}';
    }

}
