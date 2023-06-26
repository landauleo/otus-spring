package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import reactor.core.publisher.Flux;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookWithComments;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final ReactiveMongoTemplate mongoTemplate;


    @Override
    public Flux<BookWithComments> findAllWithComments() {
        Aggregation aggregation = newAggregation(
                lookup("comments", "_id", "commentedBook._id", "comments")
        );
        return mongoTemplate.aggregate(aggregation, Book.class, BookWithComments.class);
    }


    public Flux<BookWithComments> findBookWithCommentsById(String id) {
        Aggregation aggregation = newAggregation(
                lookup("comments", "_id", "commentedBook._id", "comments"),
                match(where("_id").is(id))
        );
        return mongoTemplate.aggregate
                (aggregation, Book.class, BookWithComments.class);
    }

}