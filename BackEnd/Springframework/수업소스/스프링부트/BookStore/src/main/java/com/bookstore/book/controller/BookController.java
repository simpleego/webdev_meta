package com.bookstore.book.controller;

import com.bookstore.book.entity.Book;
import com.bookstore.book.service.BookService;
import com.bookstore.users.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/list")
    public String bookList(Model model){
        System.out.println("BookController bookList");
        model.addAttribute("books",bookService.findAll());
        return "home";
    }

    @GetMapping("/book/detail/{id}")
    public String bookEditForm(@PathVariable int id, Model model){
        Book book = (Book) bookService.findById(id).orElseThrow(
                ()->new IllegalArgumentException("Invaild user id "));
        model.addAttribute("book",book);
        System.out.println("book : "+book);
        return "books/detailBook";
    }

//    @PostMapping("/user/detail")
//    public String userEdit(@ModelAttribute User user){
//        bookService.update(user);
//        return "redirect:/user/list";
//    }

}
