package com.springprac.learningjdbc.dao.impl;

import com.springprac.learningjdbc.dao.AuthorDao;
import com.springprac.learningjdbc.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; // inject
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update(
                "INSERT INTO author (id, name, age) VALUES (?, ?, ?)",
                author.getId(), author.getName(), author.getAge()
        );

    }

    @Override
    public Optional<Author> findOne(long authorId) {
        List<Author> results = jdbcTemplate.query(
                "SELECT id, name, age FROM author WHERE id = ? LIMIT 1",
                new AuthorRowMapper(), authorId
        );
        return results.stream().findFirst();
    }

    @Override
    public List<Author> find() {
        List<Author> results = jdbcTemplate.query(
                "SELECT id, name, age FROM author",
                new AuthorRowMapper()
        );
        return results;
    }

    @Override
    public void update(long id, Author author) {
        jdbcTemplate.update(
                "UPDATE author SET id = ?, name = ?, age = ? WHERE id = ?",
                author.getId(), author.getName(), author.getAge(), id
        );
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(
                "DELETE FROM author WHERE id = ?",
                id
        );
    }

    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }

}
