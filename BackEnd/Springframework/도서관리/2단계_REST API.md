#  RESTful API로 
> API는 외부 시스템이나 프론트엔드 앱에서 데이터를 JSON 형식으로 주고받을 수 있도록 설계

---

## 🌐 REST API 설계 개요

| 리소스 | URL 예시 | 설명 |
|--------|----------|------|
| 도서 | `/api/books` | 도서 목록, 등록, 수정, 삭제 |
| 회원 | `/api/customers` | 회원 정보 조회 및 관리 |
| 주문 | `/api/orders` | 주문 생성, 조회, 상태 변경 |

---

## 1️⃣ 기본 설정

### 📦 의존성 추가 (Spring Boot 기준)

```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

---

## 2️⃣ 도서 API 예시

### 📄 BookController.java

```java
@RestController
@RequestMapping("/api/books")
public class BookApiController {

    private final BookService service;

    public BookApiController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        Book book = service.findById(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        service.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book updated) {
        Book book = service.findById(id);
        if (book == null) return ResponseEntity.notFound().build();
        updated.setId(id);
        service.update(updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 3️⃣ 회원 API 예시

### 📄 CustomerApiController.java

```java
@RestController
@RequestMapping("/api/customers")
public class CustomerApiController {

    private final CustomerService service;

    public CustomerApiController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        Customer c = service.findById(id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        service.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
}
```

---

## 4️⃣ 주문 API 예시

### 📄 OrderApiController.java

```java
@RestController
@RequestMapping("/api/orders")
public class OrderApiController {

    private final OrderService service;

    public OrderApiController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        service.placeOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable int id, @RequestParam String status) {
        boolean updated = service.updateStatus(id, status);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
```

---

## 5️⃣ 테스트 예시 (Postman 또는 curl)

```bash
# 도서 목록 조회
curl -X GET http://localhost:8080/api/books

# 도서 등록
curl -X POST http://localhost:8080/api/books \
     -H "Content-Type: application/json" \
     -d '{"title":"Spring Boot","author":"박종천","price":32000}'
```

---

## 6️⃣ 보안 및 인증 확장 제안

- JWT 기반 인증 추가 가능
- `/api/admin/**` 경로는 관리자만 접근
- Swagger UI로 API 문서 자동 생성 가능

---

이제 REST API 기반의 백엔드가 완성되었습니다!  
프론트엔드나 모바일 앱과 연동하거나, 외부 시스템과 통합할 때 매우 유용하게 쓰일 수 있어요.  
다음 단계로는 Swagger 문서화, JWT 인증, 또는 프론트엔드 연동으로 확장할 수 있습니다. 어떤 방향으로 이어갈까요? 😄
