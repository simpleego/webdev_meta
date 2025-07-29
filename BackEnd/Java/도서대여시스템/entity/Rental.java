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

    @Override
    public String toString() {
        return "Rental{" +
                "book=" + book +
                ", customer=" + customer +
                ", rentDate=" + rentDate +
                '}';
    }

}
