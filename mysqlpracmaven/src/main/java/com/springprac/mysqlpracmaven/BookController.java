package com.springprac.mysqlpracmaven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// This means that this
// class is a Controller
@Controller

// This means URL's start with /geek (after Application path)
@RequestMapping(path="/geek")
public class BookController {

    // This means to get the bean called geekuserRepository
    // Which is auto-generated by Spring, we will use it
    // to handle the data
    @Autowired
    private BookRepository bookRepository;

    // Map ONLY POST Requests
    @PostMapping(path="/addbook")
    public @ResponseBody String addBooks (@RequestParam String bookName
            , @RequestParam String isbnNumber) {

        // @ResponseBody means the returned String
        // is the response, not a view name
        // @RequestParam means it is a parameter
        // from the GET or POST request

        Book book = new Book();
        book.setBookName(bookName);
        book.setIsbnNumber(isbnNumber);
        bookRepository.save(book);
        return "Details got Saved";
    }

    @GetMapping(path="/books")
    public @ResponseBody Iterable<Book> getAllUsers() {
        // This returns a JSON or XML with the Book
        return bookRepository.findAll();
    }
}