package entity;

import java.time.LocalDate;

public class Rental {
    Book book;
    Customer customer;
    LocalDate rentDate;

    public Rental(Book book, Customer customer, LocalDate rentDate) {
        this.book = book;
        this.customer = customer;
        this.rentDate = rentDate;
    }

    public Book getBook() {
        return book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "book=" + book +
                ", customer=" + customer +
                ", rentDate=" + rentDate +
                '}';
    }

}
