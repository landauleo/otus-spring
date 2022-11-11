package ru.otus.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;

    @Override
    public void insert(Book book, Genre genre, Author author) {
        jdbc.update("INSERT INTO book (id, name, genre_id, author_id) VALUES (:id, :name, :genre_id, :author_id)",
                Map.of("id", book.getId(), "name", book.getName(), "genre_id", genre.getId(), "author_id", author.getId()));
    }

    @Override
    public void update(Book book, Genre genre, Author author) {
        jdbc.update("UPDATE book SET name = :name, genre_id = :genre_id, author_id = :author_id WHERE id = :id",
                Map.of("id", book.getId(), "name", book.getName(), "genre_id", genre.getId(), "author_id", author.getId()));
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("select id, name, genre_id, author_id from book where id = :id",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT id, name, genre_id, author_id FROM book", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM book WHERE id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long genreId = resultSet.getLong("genre_id");
            long authorId = resultSet.getLong("author_id");
            return new Book(id, name, genre, ); //TODO тут вытаскивать из другого DAO, ета ок?
        }

    }

}
