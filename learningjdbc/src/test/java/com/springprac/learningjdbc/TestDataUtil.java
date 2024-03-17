package com.springprac.learningjdbc;

import com.springprac.learningjdbc.domain.Author;
import com.springprac.learningjdbc.domain.Book;

public final class TestDataUtil {

    private TestDataUtil() {}

    public static Author createTestAuthorA() {
        Author author = Author.builder()
                .id(1L)
                .name("Omar")
                .age(50)
                .build();
        return author;
    }

    public static Author createTestAuthorB() {
        Author author = Author.builder()
                .id(2L)
                .name("Ali")
                .age(40)
                .build();
        return author;
    }

    public static Author createTestAuthorC() {
        Author author = Author.builder()
                .id(3L)
                .name("Abu Bakr")
                .age(60)
                .build();
        return author;
    }

    public static Book createTestBook() {
        Book book = Book.builder()
                .isbn("12344")
                .title("Omar")
                .authorId(1L)
                .build();
        return book;
    }

    public static Book createTestBookA() {
        Book book = Book.builder()
                .isbn("12344")
                .title("Kitab-ul-Omar")
                .authorId(1L)
                .build();
        return book;
    }

    public static Book createTestBookB() {
        Book book = Book.builder()
                .isbn("4545645")
                .title("Kitab-ul-Ali")
                .authorId(2L)
                .build();
        return book;
    }

    public static Book createTestBookC() {
        Book book = Book.builder()
                .isbn("6436456")
                .title("Kitab-ul-Abu Bakr")
                .authorId(3L)
                .build();
        return book;
    }
}
