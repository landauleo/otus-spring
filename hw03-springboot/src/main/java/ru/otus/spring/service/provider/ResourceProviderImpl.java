package ru.otus.spring.service.provider;

public class ResourceProviderImpl implements ResourceProvider {

    private final LocaleProvider localeProvider;

    private final String fileBaseName;

    public ResourceProviderImpl(LocaleProvider localeProvider, String fileBaseName) {
        this.localeProvider = localeProvider;
        this.fileBaseName = fileBaseName;
    }

    @Override
    public String getFilename() {
        return localeProvider.getLocale() + "_" + fileBaseName;
    }

}
