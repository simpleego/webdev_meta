package com.bookstore.book.controller;

import com.bookstore.book.entity.Book;
import com.bookstore.book.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/books")
public class BookAPIController {

    private final BookService bookService;

    public BookAPIController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll(); // JSON 배열로 자동 변환됨
    }
}


