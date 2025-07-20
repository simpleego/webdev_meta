package step01.entity.bookui.entity;

public class Book {
    int bookid;
    String bookname;
    String publisher;
    int price;

    public Book(int bookid, String bookname, String publisher, int price) {
        this.bookid = bookid;
        this.bookname = bookname;
        this.publisher = publisher;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%d | %s | %s | %d원", bookid, bookname, publisher, price);
    }
}