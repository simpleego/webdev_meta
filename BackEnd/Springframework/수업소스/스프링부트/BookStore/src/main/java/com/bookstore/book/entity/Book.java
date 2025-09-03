package com.bookstore.book.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    private int bookid;
    private String bookname;
    private String writer;
    private String publisher;
    private int price;
    private String imgurl;
    private String content;
}
