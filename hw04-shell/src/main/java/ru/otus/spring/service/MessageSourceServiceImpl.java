package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.provider.LocaleProvider;

@Service
public class MessageSourceServiceImpl implements MessageSourceService{

    private final MessageSource messageSource;
    private final LocaleProvider localeProvider;
    private final IOService ioService;

    public MessageSourceServiceImpl(MessageSource messageSource, LocaleProvider localeProvider, IOService ioService) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
        this.ioService = ioService;
    }

    @Override
    public void printMessage(String code, String[] args) {
        ioService.outputString(messageSource.getMessage(code, args, localeProvider.getLocale()));
    }

}
