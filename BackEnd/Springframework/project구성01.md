# Spring Framework + JSP 개발환경 설정 가이드 (IntelliJ IDEA)

## 1. 프로젝트 생성

### 1.1 IntelliJ IDEA에서 새 프로젝트 생성
1. IntelliJ IDEA 실행
2. **File > New > Project** 선택
3. **Spring Initializr** 선택
4. 프로젝트 설정:
   - **Name**: `bookstore-management`
   - **Language**: Java
   - **Type**: Maven
   - **Group**: `com.bookstore`
   - **Artifact**: `bookstore-management`
   - **Package name**: `com.bookstore.management`
   - **Java**: 17

### 1.2 Dependencies 선택
- **Spring Boot DevTools**
- **Spring Web**
- **Spring JDBC**
- **MySQL Driver** (또는 H2 Database for testing)
- **Validation**

## 2. 프로젝트 구조 설정

생성된 프로젝트의 기본 구조:
```
bookstore-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bookstore/management/
│   │   │       ├── BookstoreManagementApplication.java
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── dao/
│   │   │       ├── config/
│   │   │       └── service/
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   │   ├── css/
│   │   │   │   ├── js/
│   │   │   │   └── images/
│   │   │   ├── templates/
│   │   │   └── application.properties
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── views/
│   └── test/
└── pom.xml
```

## 3. pom.xml 설정

기존 `pom.xml`에 JSP 관련 dependency 추가:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>
    
    <groupId>com.bookstore</groupId>
    <artifactId>bookstore-management</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>war</packaging>
    <name>bookstore-management</name>
    <description>Bookstore Management System</description>
    
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        
        <!-- JSP Support -->
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
        </dependency>
        
        <dependency>
            <groupId>jakarta.servlet.jsp.jstl</groupId>
            <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>jakarta.servlet.jsp.jstl</artifactId>
        </dependency>
        
        <!-- Database -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        
        <!-- H2 Database for testing -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
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
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

## 4. application.properties 설정

`src/main/resources/application.properties` 파일 설정:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/bookstore

# JSP Configuration
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

# Database Configuration (H2 for development)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true

# For MySQL (Production)
# spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
# spring.datasource.username=root
# spring.datasource.password=yourpassword
# spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver

# Logging
logging.level.com.bookstore.management=DEBUG
logging.level.org.springframework.web=DEBUG

# DevTools
spring.devtools.restart.enabled=true
```

## 5. IntelliJ IDEA 추가 설정

### 5.1 Project Structure 설정
1. **File > Project Structure** (Ctrl+Alt+Shift+S)
2. **Modules** 탭에서 프로젝트 선택
3. **Sources** 탭에서:
   - `src/main/webapp` 폴더를 **Web Resource Directories**로 설정
   - `src/main/webapp/WEB-INF/views`를 **Source Folders**로 추가

### 5.2 Facets 설정
1. **Project Structure > Facets**
2. **Web** facet 추가:
   - **Web Resource Directory**: `src/main/webapp`
   - **Source Roots**: `src/main/webapp/WEB-INF/views`

### 5.3 Run Configuration 설정
1. **Run > Edit Configurations**
2. **+** 버튼 클릭 > **Spring Boot** 선택
3. 설정:
   - **Name**: BookstoreApplication
   - **Main class**: `com.bookstore.management.BookstoreManagementApplication`
   - **Use classpath of module**: bookstore-management
   - **JRE**: 17

## 6. 필수 디렉토리 생성

프로젝트 루트에서 다음 디렉토리들을 생성:

```bash
mkdir -p src/main/webapp/WEB-INF/views
mkdir -p src/main/webapp/WEB-INF/views/book
mkdir -p src/main/webapp/WEB-INF/views/customer
mkdir -p src/main/webapp/WEB-INF/views/order
mkdir -p src/main/resources/static/css
mkdir -p src/main/resources/static/js
mkdir -p src/main/resources/static/images
```

## 7. IDE 플러그인 설치 (선택사항)

IntelliJ IDEA에서 개발 효율성을 높이기 위한 플러그인:
1. **Spring Boot Assistant**
2. **JPA Buddy**
3. **SonarLint**
4. **Rainbow Brackets**

## 8. 프로젝트 빌드 및 실행 확인

1. Maven reload: 우측 Maven 탭 > Reload 버튼 클릭
2. 프로젝트 빌드: **Build > Build Project** (Ctrl+F9)
3. 애플리케이션 실행: **Run > Run 'BookstoreApplication'**
4. 브라우저에서 `http://localhost:8080/bookstore` 접속 확인

## 다음 단계

개발환경 설정이 완료되면 다음 단계로:
1. 데이터베이스 테이블 생성 SQL 스크립트 작성
2. Model 클래스 생성 (Book, Customer, Order)
3. DAO 인터페이스 및 구현 클래스 생성
4. Service 클래스 구현
5. Controller 클래스 구현
6. JSP 뷰 파일 작성
7. CSS/JavaScript 리소스 추가

## JDBC 사용 시 추가 고려사항

### 데이터베이스 초기화 설정
`src/main/resources/schema.sql` 파일을 생성하여 테이블 생성:

```sql
-- Book Table
CREATE TABLE IF NOT EXISTS book (
    bookid INT PRIMARY KEY AUTO_INCREMENT,
    bookname VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    price INT NOT NULL
);

-- Customer Table
CREATE TABLE IF NOT EXISTS customer (
    custid INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    phone VARCHAR(20)
);

-- Orders Table
CREATE TABLE IF NOT EXISTS orders (
    orderid INT PRIMARY KEY AUTO_INCREMENT,
    custid INT NOT NULL,
    bookid INT NOT NULL,
    saleprice INT NOT NULL,
    orderdate DATE NOT NULL,
    FOREIGN KEY (custid) REFERENCES customer(custid),
    FOREIGN KEY (bookid) REFERENCES book(bookid)
);
```

`src/main/resources/data.sql` 파일로 초기 데이터 삽입 가능

### DatabaseConfig 클래스 생성 고려
JDBC Template 설정 및 트랜잭션 관리를 위한 설정 클래스 작성 권장

## 주의사항

- JDK 17 사용 시 Spring Boot 3.x 버전과 호환성 확인
- JSP 사용 시 Spring Boot는 기본적으로 내장 톰캣에서 JSP를 지원하므로 별도 설정 필요
- 개발 단계에서는 H2 데이터베이스 사용, 운영에서는 MySQL로 변경 가능
- JDBC 사용 시 수동으로 SQL 쿼리 작성 및 ResultSet 처리 필요
- 트랜잭션 관리는 @Transactional 어노테이션 사용 권장
