# DB Mybatis
> 도서 관리 프로그램을 **MyBatis 기반**
> MyBatis는 SQL 쿼리를 XML 파일에 작성하여 자바 코드와 분리하므로, SQL 관리가 더 유연하고 편리해지는 장점이 있습니다.

-----

### \#\# 1단계: 의존성 및 설정 변경

#### **`build.gradle`**

`spring-boot-starter-jdbc` 의존성을 제거하고 `mybatis-spring-boot-starter`를 추가합니다.

```groovy
dependencies {
    // ... 기존 의존성
    // implementation 'org.springframework.boot:spring-boot-starter-jdbc' // 이 부분을 주석 처리하거나 삭제
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3' // MyBatis 의존성 추가

    // ... 나머지 의존성
}
```

> **참고**: 의존성 변경 후에는 반드시 Gradle 프로젝트를 새로고침(Reload) 해주세요.

#### `application.properties`

MyBatis 설정을 추가합니다. Mapper XML 파일의 위치와 Domain 객체의 별칭(Alias)을 지정합니다.

```properties
# ... DB 설정 생략

# MyBatis 설정
# Mapper XML 파일 경로 설정
mybatis.mapper-locations=classpath:mappers/**/*.xml
# com.example.project.domain 패키지 내 클래스들의 별칭을 자동으로 생성 (예: Customer)
mybatis.type-aliases-package=com.example.project.domain
# DB의 snake_case 컬럼명을 Java의 camelCase 프로퍼티로 자동 매핑
mybatis.configuration.map-underscore-to-camel-case=true
```

-----

### \#\# 2단계: Repository -\> Mapper로 전환

기존의 `Repository` 클래스들을 삭제하고, MyBatis의 `Mapper` 인터페이스와 XML 파일을 생성합니다.

#### 🗑️ **기존 Repository 클래스 삭제**

  * `CustomerRepository.java`
  * `BookRepository.java`
  * `OrderRepository.java`

#### ✨ **Mapper 인터페이스 생성**

`com.example.project.repository` 패키지 또는 새로운 `com.example.project.mapper` 패키지에 아래 인터페이스들을 생성합니다. `@Mapper` 어노테이션을 붙여 MyBatis가 인식하도록 합니다.

  * **`CustomerMapper.java`**

    ```java
    package com.example.project.mapper;

    import com.example.project.domain.Customer;
    import org.apache.ibatis.annotations.Mapper;
    import java.util.Optional;

    @Mapper
    public interface CustomerMapper {
        void save(Customer customer);
        Optional<Customer> findByUsername(String username);
        void update(Customer customer);
    }
    ```

  * **`BookMapper.java`**

    ```java
    package com.example.project.mapper;

    import com.example.project.domain.Book;
    import org.apache.ibatis.annotations.Mapper;
    import java.util.List;
    import java.util.Optional;

    @Mapper
    public interface BookMapper {
        void save(Book book);
        Optional<Book> findById(Long bookId);
        List<Book> findAll();
    }
    ```

  * **`OrderMapper.java`**

    ```java
    package com.example.project.mapper;

    import com.example.project.domain.Order;
    import org.apache.ibatis.annotations.Mapper;
    import java.util.List;

    @Mapper
    public interface OrderMapper {
        void save(Order order);
        List<Order> findByCustId(Long custid);
    }
    ```

-----

### \#\# 3단계: Mapper XML 파일 작성

`src/main/resources/` 경로 아래에 `mappers` 디렉토리를 만들고, 그 안에 Mapper 인터페이스와 1:1로 매핑되는 XML 파일들을 작성합니다.

#### `mappers/CustomerMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.project.mapper.CustomerMapper">

    <insert id="save" parameterType="Customer">
        INSERT INTO Customer(name, username, password, address, phone)
        VALUES(#{name}, #{username}, #{password}, #{address}, #{phone})
    </insert>

    <update id="update" parameterType="Customer">
        UPDATE Customer
        SET name = #{name},
            password = #{password},
            address = #{address},
            phone = #{phone}
        WHERE custid = #{custid}
    </update>

    <select id="findByUsername" parameterType="String" resultType="Customer">
        SELECT * FROM Customer WHERE username = #{username}
    </select>
</mapper>
```

#### `mappers/BookMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.project.mapper.BookMapper">

    <insert id="save" parameterType="Book" useGeneratedKeys="true" keyProperty="bookid">
        INSERT INTO Book(bookname, publisher, price, original_filename, stored_filename)
        VALUES(#{bookname}, #{publisher}, #{price}, #{originalFilename}, #{storedFilename})
    </insert>

    <select id="findById" parameterType="long" resultType="Book">
        SELECT * FROM Book WHERE bookid = #{bookId}
    </select>

    <select id="findAll" resultType="Book">
        SELECT * FROM Book
    </select>
</mapper>
```

#### `mappers/OrderMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.project.mapper.OrderMapper">

    <insert id="save" parameterType="Order">
        INSERT INTO Orders(custid, bookid, saleprice, orderdate)
        VALUES(#{custid}, #{bookid}, #{saleprice}, #{orderdate})
    </insert>

    <select id="findByCustId" parameterType="long" resultType="Order">
        SELECT * FROM Orders WHERE custid = #{custid}
    </select>
</mapper>
```

-----

### \#\# 4단계: 서비스 및 컨트롤러 계층 수정

기존 Repository를 주입받던 부분을 새로 만든 Mapper 인터페이스를 주입받도록 수정합니다.

#### **`MemberService.java` 수정**

```java
// ...
import com.example.project.mapper.CustomerMapper; // Mapper import
// ...

@Service
public class MemberService {
    private final CustomerMapper customerMapper; // CustomerRepository -> CustomerMapper
    private final PasswordEncoder passwordEncoder;

    public MemberService(CustomerMapper customerMapper, PasswordEncoder passwordEncoder) { // 생성자 수정
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // ... join, login, updateMember 메서드 내부의
    // customerRepository.save() -> customerMapper.save()
    // customerRepository.findByUsername() -> customerMapper.findByUsername()
    // customerRepository.update() -> customerMapper.update()
    // 와 같이 메서드 호출 부분만 변경하면 됩니다. (메서드명은 동일하게 만들었음)
}
```

#### **`BookService.java` 수정**

```java
// ...
import com.example.project.mapper.BookMapper; // Mapper import
// ...

@Service
public class BookService {
    private final BookMapper bookMapper; // BookRepository -> BookMapper
    private final FileStore fileStore;

    public BookService(BookMapper bookMapper, FileStore fileStore) { // 생성자 수정
        this.bookMapper = bookMapper;
        this.fileStore = fileStore;
    }

    // 도서 ID로 조회하는 서비스 메서드 추가 (컨트롤러에서 필요)
    public Optional<Book> findBookById(Long bookId) {
        return bookMapper.findById(bookId);
    }

    // ... findBooks, saveBook 메서드 내부의
    // bookRepository.findAll() -> bookMapper.findAll()
    // bookRepository.save() -> bookMapper.save()
    // 로 변경합니다.
}
```

#### **`OrderService.java` 수정**

```java
// ...
import com.example.project.mapper.OrderMapper; // Mapper import
// ...

@Service
public class OrderService {
    private final OrderMapper orderMapper; // OrderRepository -> OrderMapper

    public OrderService(OrderMapper orderMapper) { // 생성자 수정
        this.orderMapper = orderMapper;
    }
    // ... createOrder, findOrdersByCustomer 메서드 내부의
    // orderRepository.save() -> orderMapper.save()
    // orderRepository.findByCustId() -> orderMapper.findByCustId()
    // 로 변경합니다.
}
```

#### **컨트롤러 수정**

컨트롤러 중 Repository를 직접 참조하던 부분이 있다면, Service를 거치도록 변경합니다.

  * **`OrderController.java` 수정**
    ```java
    // ...
    import com.example.project.service.BookService; // BookService import

    @Controller
    public class OrderController {
        private final OrderService orderService;
        private final BookService bookService; // BookRepository -> BookService

        public OrderController(OrderService orderService, BookService bookService) { // 생성자 수정
            this.orderService = orderService;
            this.bookService = bookService;
        }

        @PostMapping("/orders/new")
        public String createOrder(@RequestParam("bookId") Long bookId, /*...*/) {
            // ...
            // bookRepository.findById(bookId) -> bookService.findBookById(bookId) 로 변경
            Optional<Book> bookOptional = bookService.findBookById(bookId);
            // ...
        }
        // ...
    }
    ```
  * **`BookController.java` 수정**
    ```java
    // ...
    @Controller
    public class BookController {
        // ...
        @GetMapping("/books/{bookId}")
        public String bookDetail(@PathVariable Long bookId, Model model) {
            // bookRepository.findById(bookId) -> bookService.findBookById(bookId) 로 변경
            Optional<Book> bookOptional = bookService.findBookById(bookId);
            // ...
        }
        // ...
    }
    ```

이제 프로젝트를 실행하면 모든 데이터베이스 연동이 MyBatis를 통해 이루어집니다. 기존과 동일하게 모든 기능이 동작해야 합니다.
