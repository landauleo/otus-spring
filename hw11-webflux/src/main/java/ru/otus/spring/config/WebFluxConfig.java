package ru.otus.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/styles/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}
