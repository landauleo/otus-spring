package ru.otus.spring.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.IOServiceStreams;
import ru.otus.spring.service.provider.LocaleProvider;
import ru.otus.spring.service.provider.LocaleProviderImpl;
import ru.otus.spring.service.provider.ResourceProvider;
import ru.otus.spring.service.provider.ResourceProviderImpl;

@Configuration
@ConfigurationProperties(prefix = "questionnaire")
public class AppConfig {

    @Value("${questionnaire.path.basename}")
    private String basename;

    @Value("${questionnaire.locale}")
    private String locale;

    @Bean
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }

    @Bean
    public LocaleProvider localeProvider() {
        return new LocaleProviderImpl(new Locale(locale));
    }

    @Bean
    public ResourceProvider resourceProvider() {
        return new ResourceProviderImpl(localeProvider(), basename);
    }

}
