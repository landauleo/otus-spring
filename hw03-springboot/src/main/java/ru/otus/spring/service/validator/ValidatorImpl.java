package ru.otus.spring.service.validator;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import ru.otus.spring.service.MessageSourceService;

@Service
public class ValidatorImpl implements Validator {

    private final MessageSourceService messageSource;

    public ValidatorImpl(MessageSourceService messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void validate(List<String> expected, List<String> actual) {
        if (Arrays.equals(expected.toArray(), actual.toArray())) {
            messageSource.printMessage("result.success", new String[]{"(◕‿◕)"});
        } else {
            messageSource.printMessage("result.fail", new String[]{Arrays.toString(expected.toArray())});
        }
    }
}
