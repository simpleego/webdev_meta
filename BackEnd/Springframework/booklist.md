// ========================= 1. Book DTO (Model) =========================
// 파일위치: src/main/java/com/bookstore/management/model/Book.java
```java
package com.bookstore.management.model;

public class Book {
    private int bookId;
    private String bookName;
    private String publisher;
    private int price;
    
    // 기본 생성자
    public Book() {}
    
    // 전체 매개변수 생성자
    public Book(int bookId, String bookName, String publisher, int price) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.publisher = publisher;
        this.price = price;
    }
    
    // Getter and Setter
    public int getBookId() {
        return bookId;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public String getBookName() {
        return bookName;
    }
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                '}';
    }
}

// ========================= 2. BookDAO 인터페이스 =========================
// 파일위치: src/main/java/com/bookstore/management/dao/BookDAO.java
package com.bookstore.management.dao;

import com.bookstore.management.model.Book;
import java.util.List;

public interface BookDAO {
    List<Book> selectAll();
    Book selectById(int bookId);
    int insert(Book book);
    int update(Book book);
    int delete(int bookId);
}

// ========================= 3. BookDAO 구현체 =========================
// 파일위치: src/main/java/com/bookstore/management/dao/impl/BookDAOImpl.java
package com.bookstore.management.dao.impl;

import com.bookstore.management.dao.BookDAO;
import com.bookstore.management.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // RowMapper 내부 클래스
    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setBookId(rs.getInt("bookid"));
            book.setBookName(rs.getString("bookname"));
            book.setPublisher(rs.getString("publisher"));
            book.setPrice(rs.getInt("price"));
            return book;
        }
    }
    
    @Override
    public List<Book> selectAll() {
        String sql = "SELECT bookid, bookname, publisher, price FROM book ORDER BY bookid";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }
    
    @Override
    public Book selectById(int bookId) {
        String sql = "SELECT bookid, bookname, publisher, price FROM book WHERE bookid = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BookRowMapper(), bookId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public int insert(Book book) {
        String sql = "INSERT INTO book (bookname, publisher, price) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, book.getBookName(), book.getPublisher(), book.getPrice());
    }
    
    @Override
    public int update(Book book) {
        String sql = "UPDATE book SET bookname = ?, publisher = ?, price = ? WHERE bookid = ?";
        return jdbcTemplate.update(sql, book.getBookName(), book.getPublisher(), 
                                 book.getPrice(), book.getBookId());
    }
    
    @Override
    public int delete(int bookId) {
        String sql = "DELETE FROM book WHERE bookid = ?";
        return jdbcTemplate.update(sql, bookId);
    }
}

// ========================= 4. BookService 클래스 =========================
// 파일위치: src/main/java/com/bookstore/management/service/BookService.java
package com.bookstore.management.service;

import com.bookstore.management.dao.BookDAO;
import com.bookstore.management.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    
    @Autowired
    private BookDAO bookDAO;
    
    /**
     * 모든 도서 조회
     */
    public List<Book> getAllBooks() {
        return bookDAO.selectAll();
    }
    
    /**
     * 특정 도서 조회
     */
    public Book getBookById(int bookId) {
        return bookDAO.selectById(bookId);
    }
    
    /**
     * 도서 추가
     */
    public boolean addBook(Book book) {
        return bookDAO.insert(book) > 0;
    }
    
    /**
     * 도서 수정
     */
    public boolean updateBook(Book book) {
        return bookDAO.update(book) > 0;
    }
    
    /**
     * 도서 삭제
     */
    public boolean deleteBook(int bookId) {
        return bookDAO.delete(bookId) > 0;
    }
}

// ========================= 5. BookController 클래스 =========================
// 파일위치: src/main/java/com/bookstore/management/controller/BookController.java
package com.bookstore.management.controller;

import com.bookstore.management.model.Book;
import com.bookstore.management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    /**
     * 도서 목록 페이지
     */
    @GetMapping("/list")
    public String list(Model model) {
        try {
            List<Book> books = bookService.getAllBooks();
            model.addAttribute("books", books);
            model.addAttribute("totalBooks", books.size());
            System.out.println("도서 목록 조회 완료: " + books.size() + "권");
            return "book/list";
        } catch (Exception e) {
            System.err.println("도서 목록 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "도서 목록을 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }
    
    /**
     * 도서 관리 메인 (리다이렉트)
     */
    @GetMapping
    public String index() {
        return "redirect:/books/list";
    }
}

// ========================= 6. HomeController 클래스 =========================
// 파일위치: src/main/java/com/bookstore/management/controller/HomeController.java
package com.bookstore.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "서점 관리 시스템");
        return "index";
    }
}

```
