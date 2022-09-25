package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Option;

import java.util.Arrays;
import java.util.List;

@Service
public class ValidatorImpl implements Validator {

    @Override
    public void validate(List<Option> expected, List<Option> actual) {
        if (Arrays.equals(expected.toArray(), actual.toArray())) {
            System.out.println("Good job, buddy!");
        } else {
            System.out.println("Sorry, bro! Right answers were:"
                    + Arrays.toString(expected.stream().map(Option::text).toArray()));
        }
    }
}
