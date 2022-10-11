package ru.otus.spring.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ValidatorImpl implements Validator {

    private final MessageSourceService messageSource;

    public ValidatorImpl(MessageSourceService messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void validate(List<String> expected, List<String> actual) {
        if (Arrays.equals(expected.toArray(), actual.toArray())) {
            messageSource.getAndPrintMessage("result.success", new String[]{"(◕‿◕)"});
        } else {
            messageSource.getAndPrintMessage("result.fail", new String[]{Arrays.toString(expected.toArray())});
        }
    }
}
