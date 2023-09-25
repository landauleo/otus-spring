package ru.otus.spring.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.rest.BookDto;

@Slf4j
@Service
public class BookService {

    private final BookClient bookClient;

    private final CircuitBreaker circuitBreaker;

    @Autowired
    public BookService(BookClient bookClient,
                       CircuitBreakerConfig circuitBreakerConfig) {
        this.bookClient = bookClient;
        this.circuitBreaker = CircuitBreakerRegistry.of(circuitBreakerConfig)
                .circuitBreaker("circuitBreakerService");
    }

    public List<BookDto> getAllBooks() {
        log.info("CLIENT: BookService getAllBooks is called");
        Supplier<List<BookDto>> listSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, bookClient::getAllBooks);

        return listSupplier.get();
    }

    public String saveBook(BookDto bookDto) {
        log.info("CLIENT: BookService saveBook is called");
        Function<BookDto, String> decorated = CircuitBreaker
                .decorateFunction(circuitBreaker, bookClient::saveBook);

        return decorated.apply(bookDto);
    }

    public void deleteBook(String id) {
        log.info("CLIENT: BookService deleteBook is called");
        Consumer<String> stringConsumer = CircuitBreaker.decorateConsumer(circuitBreaker, bookClient::deleteBook);

        stringConsumer.accept(id);
    }

}
