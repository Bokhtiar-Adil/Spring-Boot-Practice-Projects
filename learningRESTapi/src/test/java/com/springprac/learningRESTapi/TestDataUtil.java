package com.springprac.learningRESTapi;


import com.springprac.learningRESTapi.domain.dto.AuthorDto;
import com.springprac.learningRESTapi.domain.dto.BookDto;
import com.springprac.learningRESTapi.domain.entities.AuthorEntity;
import com.springprac.learningRESTapi.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil() {}

    public static AuthorEntity createTestAuthorA() {
        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(1L)
                .name("Omar")
                .age(50)
                .build();
        return authorEntity;
    }

    public static AuthorEntity createTestAuthorB() {
        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(2L)
                .name("Ali")
                .age(40)
                .build();
        return authorEntity;
    }

    public static AuthorEntity createTestAuthorC() {
        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(3L)
                .name("Abu Bakr")
                .age(60)
                .build();
        return authorEntity;
    }

    public static BookEntity createTestBook(final AuthorEntity authorEntity) {
        BookEntity bookEntity = BookEntity.builder()
                .isbn("12344")
                .title("Omar")
                .authorEntity(authorEntity)
                .build();
        return bookEntity;
    }

    public static BookEntity createTestBookA(final AuthorEntity authorEntity) {
        BookEntity bookEntity = BookEntity.builder()
                .isbn("12344")
                .title("Kitab-ul-Omar")
                .authorEntity(authorEntity)
                .build();
        return bookEntity;
    }

    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        BookEntity bookEntity = BookEntity.builder()
                .isbn("4545645")
                .title("Kitab-ul-Ali")
                .authorEntity(authorEntity)
                .build();
        return bookEntity;
    }

    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        BookEntity bookEntity = BookEntity.builder()
                .isbn("6436456")
                .title("Kitab-ul-Abu Bakr")
                .authorEntity(authorEntity)
                .build();
        return bookEntity;
    }

    public static BookDto createTestBookDtoA(final AuthorDto authorDto) {
        BookDto bookDto = BookDto.builder()
                .isbn("12344")
                .title("Kitab-ul-Omar")
                .author(authorDto)
                .build();
        return bookDto;
    }

    public static AuthorDto createTestAuthorDtoA() {
        AuthorDto authorDto = AuthorDto.builder()
                .id(1L)
                .name("Omar")
                .age(50)
                .build();
        return authorDto;
    }
}
