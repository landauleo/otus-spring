package ru.otus.spring.service.processor;

public interface InputService {
    int readInt();

    int readIntWithPrompt(String prompt);

    String readStringWithPrompt(String prompt);

    String readString();

}
