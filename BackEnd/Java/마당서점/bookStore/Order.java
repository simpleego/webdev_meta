package bookStore;

import java.util.Date;

class Order {
    private Customer customer;
    private Book book;
    private int salePrice;
    private Date orderDate;
    private Bookstore bookstore;

    public Order(Customer customer, Book book, int salePrice, Date orderDate, Bookstore bookstore) {
        this.customer = customer;
        this.book = book;
        this.salePrice = salePrice;
        this.orderDate = orderDate;
        this.bookstore = bookstore;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Bookstore getBookstore() {
        return bookstore;
    }

    public void setBookstore(Bookstore bookstore) {
        this.bookstore = bookstore;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", book=" + book +
                ", salePrice=" + salePrice +
                ", orderDate=" + orderDate +
                ", bookstore=" + bookstore +
                '}';
    }
}
