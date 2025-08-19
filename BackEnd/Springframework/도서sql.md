```sql
-- ========================= schema.sql =========================
-- 파일위치: src/main/resources/schema.sql

-- 기존 테이블 삭제
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS customer;

-- Book 테이블 생성
CREATE TABLE book (
    bookid INT PRIMARY KEY AUTO_INCREMENT,
    bookname VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    price INT NOT NULL
);

-- Customer 테이블 생성
CREATE TABLE customer (
    custid INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    phone VARCHAR(20)
);

-- Orders 테이블 생성
CREATE TABLE orders (
    orderid INT PRIMARY KEY AUTO_INCREMENT,
    custid INT NOT NULL,
    bookid INT NOT NULL,
    saleprice INT NOT NULL,
    orderdate DATE NOT NULL,
    FOREIGN KEY (custid) REFERENCES customer(custid),
    FOREIGN KEY (bookid) REFERENCES book(bookid)
);

-- ========================= data.sql =========================
-- 파일위치: src/main/resources/data.sql

-- Book 데이터 삽입
INSERT INTO book (bookname, publisher, price) VALUES ('축구의 역사', '굿스포츠', 7000);
INSERT INTO book (bookname, publisher, price) VALUES ('축구 아는 여자', '나무수', 13000);
INSERT INTO book (bookname, publisher, price) VALUES ('축구의 이해', '대한미디어', 22000);
INSERT INTO book (bookname, publisher, price) VALUES ('골프 바이블', '대한미디어', 35000);
INSERT INTO book (bookname, publisher, price) VALUES ('피겨 교본', '굿스포츠', 8000);
INSERT INTO book (bookname, publisher, price) VALUES ('베구 단계별기술', '굿스포츠', 6000);
INSERT INTO book (bookname, publisher, price) VALUES ('야구의 추억', '이상미디어', 20000);
INSERT INTO book (
```
