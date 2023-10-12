package ru.otus.spring.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.rest.BookDto;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Client для работы с книгами")
class BookClientTest {

    @Mock
    private BookClient bookClient;

    private CircuitBreaker circuitBreaker;

    @BeforeEach
    void setUp() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(20)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(8)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        circuitBreaker = registry.circuitBreaker("circuitBreakerService");
    }

    @Test
    void getAllBooksTest() {
        Supplier<List<BookDto>> listSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, bookClient::getAllBooks);

        when(bookClient.getAllBooks()).thenThrow(new RuntimeException());

        for (int i = 0; i < 10; i++) {
            try {
                listSupplier.get();
            } catch (RuntimeException ignore) {
            }
        }

        verify(bookClient, times(5)).getAllBooks();
    }

    @Test
    void saveBookTest() {
        Function<BookDto, String> decorated = CircuitBreaker
                .decorateFunction(circuitBreaker, bookClient::saveBook);
        when(bookClient.saveBook(new BookDto())).thenThrow(new RuntimeException());

        for (int i = 0; i < 10; i++) {
            try {
                decorated.apply(new BookDto());
            } catch (RuntimeException ignore) {
            }
        }

        verify(bookClient, times(5)).saveBook(new BookDto());
    }

    @Test
    void deleteBookTest() {
        Consumer<String> stringConsumer = CircuitBreaker.decorateConsumer(circuitBreaker, bookClient::deleteBook);
        doThrow(new RuntimeException()).when(bookClient).deleteBook("123");

        for (int i = 0; i < 10; i++) {
            try {
                stringConsumer.accept("123");
            } catch (RuntimeException ignore) {
            }
        }

        verify(bookClient, times(5)).deleteBook("123");
    }

}