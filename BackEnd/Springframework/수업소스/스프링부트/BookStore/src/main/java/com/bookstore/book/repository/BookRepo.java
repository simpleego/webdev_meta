package com.bookstore.book.repository;

import com.bookstore.book.entity.Book;
import com.bookstore.users.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepo {
    private JdbcTemplate jdbc;

    public BookRepo(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        return jdbc.query(sql,bookRowMapper());
    }

    private RowMapper<Book> bookRowMapper() {
        return (rs, rowNum) -> {
            return new Book(
                    rs.getInt("bookid"),
                    rs.getString("bookname"),
                    rs.getString("writer"),
                    rs.getString("publisher"),
                    rs.getInt("price"),
                    rs.getString("imgurl"),
                    rs.getString("content")
            );
        };
    }

    public Optional<Book> findById(int bookid) {
        System.out.println("==> book findById"+bookid);
        String sql = "SELECT * FROM book WHERE bookid = ?";
        Book book = jdbc.queryForObject(sql, bookRowMapper(), bookid);
        System.out.println("==> book : "+book);
        return Optional.ofNullable(book);
    }
}
