package ru.otus.spring.mongock.changelog;

import java.util.List;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.security.crypto.bcrypt.BCrypt;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.User;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.repository.UserRepository;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "insertAuthors", author = "landauleo", runAlways = true)
    public void insertAuthors(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("author");
        List<Document> list = List.of(
                new Document().append("name", "Stieg Larsson"),
                new Document().append("name", "J.R.R. Tolkien"),
                new Document().append("name", "J.K. Rowling"),
                new Document().append("name", "Douglas Adams"));
        myCollection.insertMany(list);
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "landauleo", runAlways = true)
    public void insertGenres(GenreRepository repository) {
        repository.saveAll(List.of(
                new Genre("poem"),
                new Genre("fable"),
                new Genre("fantasy"),
                new Genre("crime"),
                new Genre("drama")));
    }

    @ChangeSet(order = "003", id = "insertUsers", author = "landauleo", runAlways = true)
    public void insertUsers(UserRepository repository) {
        repository.saveAll(List.of(
                new User("habibi", BCrypt.hashpw("pass", BCrypt.gensalt()))));
    }

}
