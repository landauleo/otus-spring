package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.controller.dto.SaveBookDto;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.ObjectNotFoundException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class BookService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public Flux<Book> findAll() {
        return bookRepository.findAll();
    }

    public Mono<Void> delete(String bookId) {
        return bookRepository.deleteById(new ObjectId(bookId));
    }

    @Transactional
    public Mono<Book> saveBook(SaveBookDto saveBookDto) {
        return upsertBook(new Book(), saveBookDto);
    }

    @Transactional
    public Mono<Book> saveBook(String bookId, SaveBookDto saveBookDto) {
        return bookRepository.findById(new ObjectId(bookId))
                .switchIfEmpty(Mono.error(() -> new ObjectNotFoundException("Book not found")))
                .flatMap(book -> upsertBook(book, saveBookDto));
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
