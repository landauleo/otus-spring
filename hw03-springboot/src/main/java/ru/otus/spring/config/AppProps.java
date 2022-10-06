package ru.otus.spring.config;

import java.util.Locale;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "questionnaire")
public class AppProps {

    @NestedConfigurationProperty
    private Path path;

    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public static class Path {

        private String enPath;

        private String ruPath;

        public void setEnPath(String enPath) {
            this.enPath = enPath;
        }

        public void setRuPath(String ruPath) {
            this.ruPath = ruPath;
        }

        public String getEnPath() {
            return enPath;
        }

        public String getRuPath() {
            return ruPath;
        }

    }

}
