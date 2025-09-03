package com.bookstore.book.service;

import com.bookstore.book.entity.Book;
import com.bookstore.book.repository.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepo bookRepo;

    public BookService(BookRepo bookRepo){
        this.bookRepo = bookRepo;
    }

    public List<Book> findAll(){
        return  bookRepo.findAll();
    }

    public Optional<Book> findById(int id) {
        return bookRepo.findById(id);
    }
}
