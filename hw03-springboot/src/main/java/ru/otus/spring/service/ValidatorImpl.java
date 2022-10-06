package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppProps;
import ru.otus.spring.domain.Option;

import java.util.Arrays;
import java.util.List;

@Service
public class ValidatorImpl implements Validator {

    private final MessageSource messageSource;
    private final AppProps appProps;

    public ValidatorImpl(MessageSource messageSource, AppProps appProps) {
        this.messageSource = messageSource;
        this.appProps = appProps;
    }

    @Override
    public void validate(List<Option> expected, List<Option> actual) {
        String messageLocalized;
        if (Arrays.equals(expected.toArray(), actual.toArray())) {
         messageLocalized = messageSource.getMessage("result.success", new String[]{"(◕‿◕)"}, appProps.getLocale());
            System.out.println(messageLocalized);
        } else {
            messageLocalized = messageSource.getMessage("result.fail", new String[]{"(◕‿◕)"}, appProps.getLocale());
            System.out.println(messageLocalized + Arrays.toString(expected.stream().map(Option::text).toArray()));
        }
    }
}
