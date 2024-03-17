package com.springprac.learningjdbc.dao;

import com.springprac.learningjdbc.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);
    Optional<Author> findOne(long l); // if author doesn't exist, returns optional empty, safer than returning null

    List<Author> find();

    void update(long id, Author author);

    void delete(long id);
}
