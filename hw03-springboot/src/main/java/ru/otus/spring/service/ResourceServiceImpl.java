package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppProps;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final AppProps appProps;

    public ResourceServiceImpl(AppProps appProps) {
        this.appProps = appProps;
    }

    @Override
    public String getFilename() {
        return appProps.getLocale().toString() + "_" + appProps.getPath().getBasename();
    }

}
