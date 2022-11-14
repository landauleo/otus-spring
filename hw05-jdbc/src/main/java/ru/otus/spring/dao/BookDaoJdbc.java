package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update("insert into book (name, author_id, genre_id) values (:name, :authorId, :genreId)", params, keyHolder, new String[]{"id"});

        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Book book) {
        jdbc.update("UPDATE book SET name = :name, genre_id = :genre_id, author_id = :author_id WHERE id = :id",
                Map.of("id", book.getId(), "name", book.getName(), "genre_id", book.getGenre().getId(), "author_id", book.getAuthor().getId()));
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("select book.id, book.name, genre_id, author_id, genre.name, author.name " +
                        "from book " +
                        "join genre on book.genre_id=genre.id " +
                        "join author on book.author_id=author.id " +
                        "where book.id = :id ",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select book.id, book.name, genre_id, author_id, genre.name, author.name " +
                "from book " +
                "join genre on book.genre_id=genre.id " +
                "join author on book.author_id=author.id ", new BookMapper());
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
            Genre genre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre.name"));
            Author author = new Author(resultSet.getLong("author_id"), resultSet.getString("author.name"));
            return new Book(id, name, genre, author);
        }

    }

}
