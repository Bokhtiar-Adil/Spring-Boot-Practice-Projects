package com.springprac.learningjpa;

import com.springprac.learningjpa.domain.Author;
import com.springprac.learningjpa.domain.Book;

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

    public static Book createTestBook(final Author author) {
        Book book = Book.builder()
                .isbn("12344")
                .title("Omar")
                .author(author)
                .build();
        return book;
    }

    public static Book createTestBookA(final Author author) {
        Book book = Book.builder()
                .isbn("12344")
                .title("Kitab-ul-Omar")
                .author(author)
                .build();
        return book;
    }

    public static Book createTestBookB(final Author author) {
        Book book = Book.builder()
                .isbn("4545645")
                .title("Kitab-ul-Ali")
                .author(author)
                .build();
        return book;
    }

    public static Book createTestBookC(final Author author) {
        Book book = Book.builder()
                .isbn("6436456")
                .title("Kitab-ul-Abu Bakr")
                .author(author)
                .build();
        return book;
    }
}
