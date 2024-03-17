package com.springprac.learningjdbc.dao.impl;

import com.springprac.learningjdbc.TestDataUtil;
import com.springprac.learningjdbc.domain.Author;
import com.springprac.learningjdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBook();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO book (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("12344"), eq("Omar"), eq(1L)
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        underTest.findOne("12344");
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM book WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("12344")
        );
    }

    @Test
    public void testThatFindGeneratesCorrectSql() {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM book"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA();
        underTest.update(book.getIsbn(), book);

        verify(jdbcTemplate).update(
                "UPDATE book SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                "12344", "Kitab-ul-Omar", 1L, "12344"
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBookA();
        underTest.delete(book.getIsbn());

        verify(jdbcTemplate).update(
                "DELETE FROM book WHERE isbn = ?",
                book.getIsbn()
        );
    }

}