package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppProps;

@Service
public class MessageSourceServiceImpl implements MessageSourceService{

    private final MessageSource messageSource;
    private final AppProps appProps;

    public MessageSourceServiceImpl(MessageSource messageSource, AppProps appProps) {
        this.messageSource = messageSource;
        this.appProps = appProps;
    }

    @Override
    public void getAndPrintMessage(String code, String[] args) {
        System.out.println(messageSource.getMessage(code, args, appProps.getLocale()));
    }

}
