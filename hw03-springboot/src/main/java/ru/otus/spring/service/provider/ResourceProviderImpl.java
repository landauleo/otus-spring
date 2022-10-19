package ru.otus.spring.service.provider;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppProps;

@Service
public class ResourceProviderImpl implements ResourceProvider {

    private final AppProps appProps;

    public ResourceProviderImpl(AppProps appProps) {
        this.appProps = appProps;
    }

    @Override
    public String getFilename() {
        return appProps.getLocale().toString() + "_" + appProps.getPath().getBasename();
    }

}
