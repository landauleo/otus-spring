package ru.otus.spring.domain;

public class Option {

    private String text;
    private boolean isRight;

    public Option(String text, boolean isRight) {
        this.text = text;
        this.isRight = isRight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

}
