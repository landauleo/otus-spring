package ru.otus.spring.config;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.resources;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@RequiredArgsConstructor
public class FunctionalEndpointsConfig {

    @Bean
    RouterFunction<ServerResponse> staticResourcePath() {
        return resources("/**", new ClassPathResource("static/"));
    }

    @Bean
    RouterFunction<ServerResponse> templatesResourcePath() {
        return resources("/**", new ClassPathResource("templates/"));
    }

    @Value("classpath:/templates/index.html")
    private Resource index;

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
//    private final ReactiveMongoTemplate mongoTemplate;
    @Bean
    public RouterFunction<ServerResponse> authorRoutes() {
        return route().GET("/api/author", accept(APPLICATION_JSON), request -> ok().body(authorRepository.findAll(), Author.class)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> genreRoutes() {
        return route().GET("/api/genre", accept(APPLICATION_JSON), request -> ok().body(genreRepository.findAll(), Genre.class)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookRoutes() {
        return route()
                .GET("/api/book", accept(APPLICATION_JSON), request -> ok().body(bookRepository.findAll(), Book.class))

                .DELETE("/api/book/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.deleteById(new ObjectId(request.pathVariable("id")))
                                .flatMap(resp -> ok().build()))
                .POST("/api/book",
                        request -> request.bodyToMono(Book.class)
                                .flatMap(book -> created(URI.create("/api/book/" + book.getId()))
                                        .body(bookRepository.save(book), Book.class))
                                .switchIfEmpty(badRequest().build()))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> staticPages() {
        return route()
                .GET("/",
                        request -> ok()
                                .contentType(MediaType.TEXT_HTML)
                                .bodyValue(index))
                .build();
    }

}
