package ru.otus.spring.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppProps;

@Service
public class MessageSourceServiceImpl implements MessageSourceService{

    private final MessageSource messageSource;
    private final Locale locale;
    private final IOService ioService;

    public MessageSourceServiceImpl(MessageSource messageSource, AppProps appProps) {
        this.messageSource = messageSource;
        this.locale = appProps.getLocale();
        this.ioService = new IOServiceStreams(System.out, System.in);;
    }

    @Override
    public void printMessage(String code, String[] args) {
        ioService.outputString(messageSource.getMessage(code, args, locale));
    }

}
