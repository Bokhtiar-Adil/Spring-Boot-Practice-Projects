package com.springprac.learningjpa.repositories;

import com.springprac.learningjpa.domain.Author;
import com.springprac.learningjpa.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
