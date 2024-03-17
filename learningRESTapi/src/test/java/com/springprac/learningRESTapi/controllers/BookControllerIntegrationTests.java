package com.springprac.learningRESTapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springprac.learningRESTapi.TestDataUtil;
import com.springprac.learningRESTapi.domain.dto.AuthorDto;
import com.springprac.learningRESTapi.domain.dto.BookDto;
import com.springprac.learningRESTapi.domain.entities.AuthorEntity;
import com.springprac.learningRESTapi.domain.entities.BookEntity;
import com.springprac.learningRESTapi.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttpStatus201Created() throws Exception{
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedAuthor() throws Exception{
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBookEntityA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].author").value(testBookEntityA.getAuthorEntity())
        );
    }


    @Test
    public void testThatGetBookReturnsHttpStatus200WhenBookExists() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }


    @Test
    public void testThatGetBookReturnsHttpStatus404WhenBookDoesNotExist() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntityA.getIsbn() + "1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetBookReturnsBookWhenBookExists() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntityA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookEntityA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(testBookEntityA.getAuthorEntity())
        );
    }

    @Test
    public void testThatFullUpdateBookReturnsHttpStatus201WhenBookDoesNotExist() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
        // BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(testAuthorDtoA);
        // testBookDtoA.setIsbn(testBookEntityA.getIsbn());
        String bookDtoJson = objectMapper.writeValueAsString(testBookDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatFullUpdateBookReturnsHttpStatus200WhenBookExists() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(testAuthorDtoA);
        testBookDtoA.setIsbn(savedBook.getIsbn());
        String bookDtoJson = objectMapper.writeValueAsString(testBookDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateBookReturnsUpdatedBook() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(testAuthorDtoA);
        testBookDtoA.setIsbn(savedBook.getIsbn());
        String bookDtoJson = objectMapper.writeValueAsString(testBookDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDtoA.getTitle())
        );
    }

//    @Test
//    public void testThatPartialUpdateBookReturnsHttpStatus201WhenBookDoesNotExist() throws Exception {
//        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
//        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
//        // BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
//
//        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
//        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(testAuthorDtoA);
//        // testBookDtoA.setIsbn(testBookEntityA.getIsbn());
//        String bookDtoJson = objectMapper.writeValueAsString(testBookDtoA);
//        mockMvc.perform(
//                MockMvcRequestBuilders.patch("/books/" + testBookEntityA.getIsbn())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(bookDtoJson)
//        ).andExpect(
//                MockMvcResultMatchers.status().isCreated()
//        );
//    }

    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus200WhenBookExists() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(testAuthorDtoA);
        testBookDtoA.setIsbn(savedBook.getIsbn());
        String bookDtoJson = objectMapper.writeValueAsString(testBookDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookEntityA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(testAuthorDtoA);
        testBookDtoA.setIsbn(savedBook.getIsbn());
        String bookDtoJson = objectMapper.writeValueAsString(testBookDtoA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDtoA.getTitle())
        );
    }

    @Test
    public void testThatDeleteBookReturnsHttpStatus204ForNonExistingBook() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/1000")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteBookReturnsHttpStatus204ForExistingBook() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(testAuthorEntityA);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
