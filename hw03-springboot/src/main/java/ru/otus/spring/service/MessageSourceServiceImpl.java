package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppProps;

@Service
public class MessageSourceServiceImpl implements MessageSourceService{

    private final MessageSource messageSource;
    private final AppProps appProps;
    private final IOService ioService;

    public MessageSourceServiceImpl(MessageSource messageSource, AppProps appProps) {
        this.messageSource = messageSource;
        this.appProps = appProps;
        this.ioService = new IOServiceStreams(System.out, System.in);;
    }

    @Override
    public void printMessage(String code, String[] args) {
        ioService.outputString(messageSource.getMessage(code, args, appProps.getLocale()));
    }

}
