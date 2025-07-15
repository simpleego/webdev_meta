package bookStore;

public class BookstoreApp {
    public static void main(String[] args) {

        int orderCount = 0;
        Order[] orders = new Order[10];

        // 책과 고객 객체 생성
        Book book1 = new Book(1, " 축구의 역사", 7000);
        Book book2 = new Book(2, " 축구 아는 여자", 13000);
        Book book3 = new Book(3, " 축구의 이해", 22000);
        Book book4 = new Book(4, " 골프 바이블", 35000);
        Book book5 = new Book(5, " 피겨 교본", 8000);
        Book book6 = new Book(6, " 배구 단계별기술", 6000);
        Book book7 = new Book(7, " 야구의 추억", 20000);
        Book book8 = new Book(8, " 야구를 부탁해", 13000);
        Book book9 = new Book(9, " 올림픽 이야기", 7500);
        Book book10 = new Book(10, " Olympic Champions", 13000);

        Book[] inventory = {book1, book2, book3, book4, book5, book6, book7, book8, book9, book10};

        Customer customer1 = new Customer(101, "박지성", "영국 맨체스터", "010-3565-8899");
        Customer customer2 = new Customer(101, "김연아", "대한민국 서울", "010-2345-1004");
        Customer customer3 = new Customer(101, "김연경", "대한민국 경기도", "010-2569-1234");
        Customer customer4 = new Customer(101, "추신수", "미국 클리블랜드", "010-3423-0129");
        Customer customer5 = new Customer(101, "박세리", "대한민국 대전", "010-2533-7789");
        Customer[] customers = {customer1, customer2, customer3, customer4, customer5};

        // 서점 객체 생성
        Bookstore myStore = new Bookstore("책나라", "서울시 서초구", inventory, customers);

        // 구매 시나리오 실행
        Order newOrder1 = myStore.purchaseBook(customer1, 1, 7000);
        orders[orderCount++] = newOrder1;

        Order newOrder2 = myStore.purchaseBook(customer1, 2, 13000);
        orders[orderCount++] = newOrder2;


        // 주문 내역 출력

        System.out.println(">>>           🧾 주문 정보           <<<");
        System.out.println("---------------------------------------");
        System.out.println("순번 | 고객명 | 도 서 명      |  판매가   ");
        System.out.println("---------------------------------------");
        int count = 0;
        for (Order order : orders) {
            System.out.printf("%3d %5s  %-10s %9s원\n", ++count, order.getCustomer().getName(), order.getBook().getTitle(),
                    String.format("%,d",order.getSalePrice()));
            if(count == orderCount) break;
        }
    }

}
