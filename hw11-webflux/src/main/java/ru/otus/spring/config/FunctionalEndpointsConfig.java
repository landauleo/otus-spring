package ru.otus.spring.config;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.controller.dto.AuthorDto;
import ru.otus.spring.controller.dto.BookDto;
import ru.otus.spring.controller.dto.GenreDto;
import ru.otus.spring.controller.dto.SaveBookDto;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.ObjectNotFoundException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.resources;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Configuration
@RequiredArgsConstructor
public class FunctionalEndpointsConfig {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Bean
    RouterFunction<ServerResponse> staticResourcePath() {
        return resources("/static/**", new ClassPathResource("static/"));
    }

    @Value("classpath:/templates/index.html")
    private Resource index;


    @Bean
    public RouterFunction<ServerResponse> authorRoutes() {
        return route().GET("/api/author", accept(APPLICATION_JSON),
                request -> ok().body(authorRepository.findAll().map(AuthorDto::toDto), AuthorDto.class)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> genreRoutes() {
        return route().GET("/api/genre", accept(APPLICATION_JSON),
                request -> ok().body(genreRepository.findAll().map(GenreDto::toDto), GenreDto.class)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookRoutes() {
        return route()
                .GET("/api/book", accept(APPLICATION_JSON),
                        request -> ok().body(bookRepository.findAll().map(BookDto::toDto), BookDto.class)
                )
                .DELETE("/api/book/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.deleteById(new ObjectId(request.pathVariable("id")))
                                .flatMap(r -> ok().build())
                )
                .POST("/api/book",
                        request -> request.bodyToMono(SaveBookDto.class)
                                .flatMap(saveBookDto -> upsertBook(new Book(), saveBookDto))
                                .map(BookDto::toDto)
                                .flatMap(bookDto -> created(URI.create("/api/book/" + bookDto.getId())).bodyValue(bookDto))
                                .onErrorResume(ObjectNotFoundException.class,
                                        e -> status(HttpStatus.NOT_FOUND).bodyValue(e.getMessage()))
                                .switchIfEmpty(badRequest().build())
                )
                .PUT("/api/book/{id}",
                        request -> request.bodyToMono(SaveBookDto.class)
                                .flatMap(bookDto -> bookRepository.findById(new ObjectId(request.pathVariable("id")))
                                        .switchIfEmpty(Mono.error(() -> new ObjectNotFoundException("Book not found")))
                                        .flatMap(book -> upsertBook(book, bookDto)))
                                .map(BookDto::toDto)
                                .flatMap(bookDto -> ok().bodyValue(bookDto))
                                .onErrorResume(ObjectNotFoundException.class,
                                        e -> status(HttpStatus.NOT_FOUND).bodyValue(e.getMessage()))
                                .switchIfEmpty(badRequest().build())
                )
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

    private Mono<Book> upsertBook(Book book, SaveBookDto bookDto) {
        return Mono.zip(
                        authorRepository.findById(new ObjectId(bookDto.getAuthorId()))
                                .switchIfEmpty(Mono.error(() -> new ObjectNotFoundException("Author not found"))),
                        genreRepository.findById(new ObjectId(bookDto.getGenreId()))
                                .switchIfEmpty(Mono.error(() -> new ObjectNotFoundException("Genre not found")))
                )
                .flatMap(tuple -> {
                    var author = tuple.getT1();
                    var genre = tuple.getT2();

                    book.setName(bookDto.getName());
                    book.setAuthor(author);
                    book.setGenre(genre);
                    return bookRepository.save(book);
                });
    }

}
