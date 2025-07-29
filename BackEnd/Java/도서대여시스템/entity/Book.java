package entity;

public class Book {
    String title;
    String author;
    String publisher;
    int year;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYear() {
        return year;
    }

    public Book(String title, String author, String publisher, int year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

    @Override
    public String toString() {
        return "도서 [" +
                " 책제목 : " + title +
                " 저자 : " + author +
                " 출판사 : " + publisher +
                " 출판년도 : " + year+ "]";
    }
}
