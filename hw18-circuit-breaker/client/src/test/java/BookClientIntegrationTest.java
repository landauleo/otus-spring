import java.util.function.Function;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.rest.BookClient;
import ru.otus.spring.rest.BookDto;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Service для работы с книгами")
class BooksClientIntegrationTest {

    private BookClient bookClient;
    private Function<BookDto, String> decorated;

    @BeforeEach
    public void setUp() {
        bookClient = mock(BookClient.class);

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(20)
                .minimumNumberOfCalls(5)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        CircuitBreaker circuitBreaker = registry.circuitBreaker("circuitBreakerService");
        decorated = CircuitBreaker.decorateFunction(circuitBreaker, bookClient::saveBook);
    }

    @Test
    public void testCircuitBreakerBookClient() {
        when(bookClient.saveBook(new BookDto())).thenThrow(new RuntimeException());

        for (int i = 0; i < 10; i++) {
            try {
                decorated.apply(new BookDto());
            } catch (Exception ignore) {
            }
        }

        verify(bookClient, times(5)).saveBook(new BookDto());
    }

}