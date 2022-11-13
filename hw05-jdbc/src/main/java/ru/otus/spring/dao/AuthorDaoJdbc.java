package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    //wraps a JdbcTemplate to provide named parameters instead of the traditional JDBC "?" placeholders
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Author author) {
        jdbc.update("insert into author (id, name) values (:id, :name)",
                Map.of("id", author.getId(), "name", author.getName()));
    }

    @Override
    public void update(Author author) {
        jdbc.update("UPDATE author set name = :name WHERE id = :id",
                Map.of("id", author.getId(), "name", author.getName()));
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject("select id, name from author where id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public Author getByName(String name) {
        return jdbc.queryForObject("select id, name from author where name = :name",
                Map.of("name", name), new AuthorMapper());
    }

    //звёздочка - грех, параметры меняются, а запросы остаются
    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, name from author", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from author where id = :id", Map.of("id", id));
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
