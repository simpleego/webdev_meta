# 스프링프레임워크 설정
# 인텔리제이 얼티메이트로 스프링 프레임워크 프로젝트 개발 가이드

## 1. 프로젝트 생성

### Spring Initializr 활용
- **File → New → Project → Spring Initializr** 선택
- 프로젝트 메타데이터 설정:
  - Group: com.example
  - Artifact: demo
  - Name: demo
  - Package name: com.example.demo
  - Packaging: Jar 또는 War
  - Java Version: 17 또는 21 권장

### 주요 의존성 선택
- **Spring Web**: REST API 및 웹 개발
- **JDBC API**: Spring JDBC 연동
- **MySQL Driver**: MySQL 데이터베이스 연결
- **Spring Security**: 보안 (필요시)
- **Spring Boot DevTools**: 개발 편의성
- **Spring Boot Actuator**: 모니터링 (필요시)

## 2. 프로젝트 구조 설정

### 권장 패키지 구조
```
src/main/java/com/example/demo/
├── DemoApplication.java (메인 클래스)
├── config/          (설정 클래스)
├── controller/      (컨트롤러)
├── service/         (서비스 계층)
├── dao/             (데이터 접근 계층 - Spring JDBC)
├── model/           (모델/DTO 클래스)
├── exception/       (예외 처리)
└── util/            (유틸리티)
```

### 리소스 구조
```
src/main/resources/
├── application.yml (또는 .properties)
├── static/         (CSS, JS, 이미지 등)
├── schema.sql      (데이터베이스 스키마)
└── data.sql        (초기 데이터)

src/main/webapp/
└── WEB-INF/
    └── views/      (JSP 파일들)
        ├── index.jsp
        ├── user/
        └── common/
```

## 3. 인텔리제이 스프링 지원 기능

### Spring Boot 대시보드
- **View → Tool Windows → Spring Boot** 활성화
- 애플리케이션 실행/중지/재시작 관리
- 엔드포인트 모니터링
- 로그 실시간 확인

### HTTP Client
- **Tools → HTTP Client → Test RESTful Web Service**
- `.http` 파일을 통한 API 테스트
- 내장된 HTTP 클라이언트로 REST API 테스트

### 데이터베이스 도구
- **View → Tool Windows → Database**
- 데이터소스 설정 및 관리
- SQL 콘솔 및 쿼리 실행
- 스키마 탐색 및 데이터 조회

## 4. 주요 설정 파일

### application.yml 기본 설정
```yaml
server:
  port: 8080
  servlet:
    context-path: /

spring:
  application:
    name: demo
  
  # MySQL 데이터소스 설정
  datasource:
    url: jdbc:mysql://localhost:3306/demo_db?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: password
    
  # Spring JDBC 설정
  sql:
    init:
      schema-locations: classpath:schema.sql
      data-locations: classpath:data.sql
      mode: always # 또는 embedded
  
  # JSP View 설정
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp

logging:
  level:
    com.example.demo: DEBUG
    org.springframework.jdbc: DEBUG
```

### Maven pom.xml 주요 의존성
```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter JDBC -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    
    <!-- MySQL Connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- JSP Support -->
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
        <scope>provided</scope>
    </dependency>
    
    <!-- JSTL -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
    </dependency>
    
    <!-- DevTools -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    
    <!-- Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 5. JSP 및 Spring JDBC 개발

### JSP 설정 및 개발
#### JSP 페이지 기본 구조
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Demo Application</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- JSP 콘텐츠 -->
</body>
</html>
```

#### 컨트롤러에서 JSP 뷰 반환
```java
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Hello World!");
        return "index"; // /WEB-INF/views/index.jsp
    }
}
```

### Spring JDBC 구성
#### DataSource 설정 (Configuration 클래스)
```java
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
```

#### DAO 클래스 예제
```java
@Repository
public class UserDao {
    
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    public UserDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    public List<User> findAll() {
        String sql = "SELECT id, name, email FROM users";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }
    
    public User findById(Long id) {
        String sql = "SELECT id, name, email FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToUser, id);
    }
    
    public int save(User user) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        return jdbcTemplate.update(sql, user.getName(), user.getEmail());
    }
    
    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
```

### MySQL 데이터베이스 스키마
#### schema.sql 파일
```sql
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### data.sql 파일
```sql
INSERT INTO users (name, email) VALUES 
    ('홍길동', 'hong@example.com'),
    ('김철수', 'kim@example.com'),
    ('이영희', 'lee@example.com');
```

## 6. 개발 효율성 향상

### Live Templates 활용
- **File → Settings → Editor → Live Templates**
- Spring 관련 커스텀 템플릿 생성
- 예: `@rest` → `@RestController` 클래스 템플릿

### Code Generation
- **Alt + Insert**: Getter/Setter, Constructor 자동 생성
- **Ctrl + O**: Override 메소드 선택
- **Ctrl + I**: Implement 메소드 선택

### Spring Boot DevTools 설정
- **File → Settings → Build → Compiler**
- "Build project automatically" 체크
- **Registry (Ctrl + Shift + Alt + /)**: "compiler.automake.allow.when.app.running" 체크

## 6. 디버깅 및 테스트

### 디버깅 설정
- 중단점(Breakpoint) 설정: **Ctrl + F8**
- 디버그 모드 실행: **Shift + F9**
- 조건부 중단점 및 로그 중단점 활용

### 테스트 환경
- **@SpringBootTest** 어노테이션 활용
- **@MockBean**으로 의존성 모킹
- **@JdbcTest** JDBC 계층 테스트
- **@WebMvcTest** 웹 계층 테스트
- MySQL TestContainers 통합 테스트

## 7. 유용한 플러그인

### 필수 플러그인
- **Spring Boot Helper**: 설정 파일 지원 강화
- **Spring Assistant**: 의존성 및 설정 도움
- **Database Navigator**: 데이터베이스 관리 강화
- **JSP Support**: JSP 문법 강조 및 자동완성

### 코드 품질 플러그인
- **SonarLint**: 코드 품질 검사
- **CheckStyle**: 코딩 컨벤션 검사
- **SpotBugs**: 버그 패턴 탐지

## 8. 프로파일별 환경 관리

### 환경별 설정 파일
```
application.yml           (기본 설정)
application-dev.yml       (개발 환경)
application-prod.yml      (운영 환경)
application-test.yml      (테스트 환경)
```

### 프로파일 활성화
- **Run Configuration**에서 VM options: `-Dspring.profiles.active=dev`
- 또는 Program arguments: `--spring.profiles.active=dev`

## 9. 모니터링 및 운영

### Spring Boot Actuator 엔드포인트
- `/actuator/health`: 애플리케이션 상태
- `/actuator/metrics`: 메트릭 정보
- `/actuator/env`: 환경 변수
- `/actuator/beans`: Spring Bean 정보

### 로그 관리
```yaml
logging:
  level:
    root: INFO
    com.example: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
  file:
    name: application.log
```

## 10. 성능 최적화 팁

### JVM 튜닝
- **Run Configuration** → VM options:
  - `-Xms512m -Xmx1024m`: 힙 메모리 설정
  - `-XX:+UseG1GC`: G1 가비지 컬렉터 사용

### 개발 환경 최적화
- **File → Settings → Build → Build Tools → Maven/Gradle**
- "Build and run using: IntelliJ IDEA" 선택
- 병렬 빌드 활성화

## 12. JSP 개발 팁

### JSP 파일 실시간 반영
- **File → Settings → Build → Compiler**
- "Build project automatically" 체크
- JSP 파일 수정 시 자동으로 웹 브라우저에 반영

### JSP 디버깅
- JSP 파일에서도 중단점 설정 가능
- **Ctrl + Shift + F12**: JSP 컴파일된 서블릿 코드 확인
- EL(Expression Language) 및 JSTL 디버깅 지원

### JSTL 자동완성
- **File → Settings → Editor → General → Auto Import**
- JSP 태그 라이브러리 자동 import 설정
- `<c:forEach>`, `<c:if>` 등 JSTL 태그 자동완성

## 14. 자주 사용하는 단축키

### 데이터베이스 연결 확인
```java
@RestController
public class HealthController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/health/db")
    public Map<String, String> checkDatabase() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return Map.of("status", "UP", "database", "MySQL");
        } catch (Exception e) {
            return Map.of("status", "DOWN", "error", e.getMessage());
        }
    }
}
```

### 트랜잭션 관리
```java
@Service
@Transactional
public class UserService {
    
    private final UserDao userDao;
    
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    
    @Transactional
    public void createUser(User user) {
        userDao.save(user);
        // 다른 비즈니스 로직...
    }
}
```

- **Ctrl + Shift + F10**: 현재 클래스/메소드 실행
- **Ctrl + F9**: 프로젝트 빌드
- **Ctrl + Shift + F9**: 현재 파일 빌드
- **Alt + Shift + F10**: Run Configuration 선택
- **Ctrl + Shift + A**: Action 검색
- **Double Shift**: 전체 검색
