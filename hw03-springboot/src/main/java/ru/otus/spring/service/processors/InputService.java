package ru.otus.spring.service.processors;

public interface InputService {
    int readInt();

    int readIntWithPrompt(String prompt);

    String readStringWithPrompt(String prompt);

    String readString();

}
