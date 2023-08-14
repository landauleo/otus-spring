package ru.otus.spring.config;

import java.util.List;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.changelog.DatabaseChangelog;
import ru.otus.spring.controller.dto.SaveBookDto;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class FunctionalEndpointsConfigTest {

    @Autowired
    @Qualifier(value = "bookRoutes")
    private RouterFunction<ServerResponse> bookRoutes;

    @Autowired
    @Qualifier(value = "authorRoutes")
    private RouterFunction<ServerResponse> authorRoutes;

    @Autowired
    @Qualifier(value = "genreRoutes")
    private RouterFunction<ServerResponse> genreRoutes;

    @Autowired
    private DatabaseChangelog dbInitializer;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        dbInitializer.initialize();
    }

    @Test
    @DisplayName("Получаем все книги")
    void bookRoutesGetTest() {
        client = WebTestClient.bindToRouterFunction(bookRoutes).build();
        List<Book> bookList = bookRepository.findAll().collectList().block();

        client.get()
                .uri("/api/book")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Book.class)
                .hasSize(3)
                .value(list -> assertThat(list)
                        .usingRecursiveComparison()
                        .ignoringExpectedNullFields()
                        .isEqualTo(bookList));
    }

    @Test
    @DisplayName("Удаляем книгу по id")
    void bookRoutesDeleteTest() {
        client = WebTestClient.bindToRouterFunction(bookRoutes).build();
        Book book = new Book(null, null, null);

        ObjectId bookId = bookRepository.save(book).block().getId();

        client.delete()
                .uri(uriBuilder -> uriBuilder.path("/api/book/{id}").build(bookId.toString()))
                .exchange()
                .expectStatus()
                .isOk();

        assertThat(bookRepository.findById(bookId).block()).isNull();
    }

    @Test
    @DisplayName("Создаем книгу")
    void bookRoutesPostTest() {
        client = WebTestClient.bindToRouterFunction(bookRoutes).build();
        String genreId = Objects.requireNonNull(genreRepository.save(new Genre("novel")).block()).getId().toString();
        String authorId = Objects.requireNonNull(authorRepository.save(new Author("Frederic Buckman")).block()).getId().toString();
        SaveBookDto saveBookDto = new SaveBookDto("Uwe's Second Life", genreId, authorId);

        client.post()
                .uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(saveBookDto)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @DisplayName("Изменяем книгу")
    void bookRoutesPutTest() {
        client = WebTestClient.bindToRouterFunction(bookRoutes).build();
        String genreId = Objects.requireNonNull(genreRepository.save(new Genre("novel")).block()).getId().toString();
        String authorId = Objects.requireNonNull(authorRepository.save(new Author("Frederic Buckman")).block()).getId().toString();
        SaveBookDto saveBookDto = new SaveBookDto("Uwe's Second Life", genreId, authorId);
        String bookId = Objects.requireNonNull(bookRepository.save(new Book(null, null, null)).block()).getId().toString();

        client.put()
                .uri(uriBuilder -> uriBuilder.path("/api/book/{id}").build(bookId))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(saveBookDto)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Получаем все жанры")
    void genreRoutesGetTest() {
        client = WebTestClient.bindToRouterFunction(genreRoutes).build();
        List<Genre> genreList = genreRepository.findAll().collectList().block();

        client.get()
                .uri("/api/genre")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Genre.class)
                .hasSize(5)
                .value(list -> assertThat(list)
                        .usingRecursiveComparison()
                        .ignoringExpectedNullFields()
                        .isEqualTo(genreList));


    }

    @Test
    @DisplayName("Получаем всех авторов")
    void authorGetTest() {
        client = WebTestClient.bindToRouterFunction(authorRoutes).build();
        List<Author> authorList = authorRepository.findAll().collectList().block();

        client.get()
                .uri("/api/author")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Author.class)
                .hasSize(4)
                .value(list -> assertThat(list)
                        .usingRecursiveComparison()
                        .ignoringExpectedNullFields()
                        .isEqualTo(authorList));


    }

}