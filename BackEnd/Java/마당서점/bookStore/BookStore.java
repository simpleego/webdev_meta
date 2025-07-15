package bookStore;

import java.util.Date;

class Bookstore {
    private String name;
    private String location;
    private Book[] inventory;
    private Customer[] customers;

    public Bookstore(String name, String location, Book[] inventory, Customer[] customers) {
        this.name = name;
        this.location = location;
        this.inventory = inventory;
        this.customers = customers;
    }

    // 고객이 책을 구매하는 메서드
    public Order purchaseBook(Customer customer, int bookId, int salePrice) {
        Book selectedBook = null;
        for (Book book : inventory) {
            if (book != null && book.getBookId() == bookId) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            System.out.println("📕 해당 ID의 책을 찾을 수 없습니다.");
            return null;
        }

        Order order = new Order(customer, selectedBook, salePrice, new Date(), this);
        System.out.println("✅ 주문 생성 완료: " + customer.getName() + "님이 \"" + selectedBook.getTitle() + "\"을 구매했습니다.");
        return order;
    }

    // Getters and Setters...
}