package ru.otus.spring.repository;

import reactor.core.publisher.Flux;
import ru.otus.spring.domain.BookWithComments;

public interface BookRepositoryCustom {
    Flux<BookWithComments> findAllWithComments();


    Flux<BookWithComments> findBookWithCommentsById(String id);

}
