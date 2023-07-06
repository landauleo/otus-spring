package ru.otus.spring.mongock.changelog;

import java.util.List;

import com.mongodb.client.result.InsertManyResult;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.driver.mongodb.reactive.util.MongoSubscriberSync;
import io.mongock.driver.mongodb.reactive.util.SubscriberSync;
import org.bson.Document;

@ChangeUnit(id = "initial", order = "1", author = "mongock")
public class DatabaseChangelog {

//    @ChangeSet(order = "000", id = "dropDb", author = "landauleo", runAlways = true)
//    public void dropDb(MongoDatabase db) {
//        db.drop();
//    }

    @Execution
    public void insertAuthors(MongoDatabase db) {
        SubscriberSync<InsertManyResult> subscriber = new MongoSubscriberSync<>();

        List<Document> authors = List.of(
                new Document().append("name", "Stieg Larsson"),
                new Document().append("name", "J.R.R. Tolkien"),
                new Document().append("name", "J.K. Rowling"),
                new Document().append("name", "Douglas Adams"));

        db.getCollection("author").insertMany(authors).subscribe(subscriber);
        subscriber.await();


//        List<Document> genres = List.of(
//                new Document().append("name", "poem"),
//                new Document().append("name", "fable"),
//                new Document().append("name", "fantasy"),
//                new Document().append("name", "crime"),
//                new Document().append("name", "drama"));
//
//        db.getCollection("genre").insertMany(genres).subscribe(subscriber);
//
//        subscriber.await();
    }

//    @ChangeSet(order = "002", id = "insertGenres", author = "landauleo", runAlways = true)
//    public void insertGenres(GenreRepository repository) {
//        SubscriberSync<Void> subscriber = new MongoSubscriberSync<>();
//        repository.saveAll(List.of(
//                        new Genre("poem"),
//                        new Genre("fable"),
//                        new Genre("fantasy"),
//                        new Genre("crime"),
//                        new Genre("drama")))
//                .subscribe(subscriber);
//        subscriber.await();
//    }

}
