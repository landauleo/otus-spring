package ru.otus.spring.service.provider;

import java.util.Locale;

public class LocaleProviderImpl implements LocaleProvider {

    private final Locale locale;

    public LocaleProviderImpl(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

}
