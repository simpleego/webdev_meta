package mainUI;

import entity.Book;
import entity.Customer;
import entity.Rental;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class LibrarySystem {
    static ArrayList<Book> bookList = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Rental> rentals = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("도서관 시스템 시작");

        // 도서등록
        System.out.println("도서 등록");
        addBook();
        addBook();
        addBook();

        // 회원등록
        System.out.println("회원 등록");
        addCustomer();
        addCustomer();

        // 도서 대여
        System.out.println("도서 대여");
        rentBook();
        rentBook();
    }

    private static void rentBook() {
        if(bookList.isEmpty() || customers.isEmpty()){
            System.out.println(" 도서 또는 회원정보가 없어서 대여할 수 없습니다.");
            return;
        }

        System.out.println("대여할 도서 선택(0~ "+(bookList.size() -1)
                             +"): ");
        for (int i=0; i<bookList.size(); i++){
            System.out.println(i+": "+bookList.get(i).getTitle());
        }

        Scanner kbd = new Scanner(System.in);

        // 대여할 책의 번호를 받음
        int bookIndex =  Integer.parseInt(kbd.nextLine());

        System.out.println(" 선택한 책은 "+bookList.get(bookIndex).getTitle());

        System.out.println("대여할 고객 선택 (0~"+(customers.size()-1)+"): ");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println(i+": "+customers.get(i).getName());            
        }
        
        int customerIndex = kbd.nextInt();
        
        // 대여 처리
        Rental rental = new Rental(bookList.get(bookIndex),
                customers.get(customerIndex),
                LocalDate.now());
        rentals.add(rental);

        // 대여 리스트 출력
        for (int i = 0; i < rentals.size(); i++) {
            System.out.println(rentals.get(i).getCustomer().getName()+
                    ","+ rentals.get(i).getBook().getTitle());
        }
    }

    private static void addCustomer() {
        Scanner kbd = new Scanner(System.in);

        System.out.print("성명:");
        String name = kbd.nextLine();

        System.out.print("아이디:");
        String id = kbd.nextLine();

        // 회원등록(컬렉션)
        customers.add(new Customer(name,id));
        for (Customer customer : customers){
            System.out.println(customer);
        }
    }

    private static void addBook() {
        Scanner kbd = new Scanner(System.in);

        System.out.print("도서명:");
        String title = kbd.nextLine();

        System.out.print("저자명:");
        String author = kbd.nextLine();

        System.out.print("출판사명:");
        String publisher = kbd.nextLine();

        System.out.print("출판년도:");
        int year = Integer.parseInt(kbd.nextLine());

        //  책 객체 생성
        Book book = new Book(title,author,publisher,year);
        bookList.add(book);

        for (Book book1: bookList){
            System.out.println(book1);
        }

    }

}
