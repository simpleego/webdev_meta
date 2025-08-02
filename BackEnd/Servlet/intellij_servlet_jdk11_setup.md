# IntelliJ IDEA 서블릿 개발환경 설정 (JDK 11)

## 📋 목차
1. [사전 준비](#1-사전-준비)
2. [IntelliJ IDEA 프로젝트 생성](#2-intellij-idea-프로젝트-생성)
3. [Tomcat 서버 설정](#3-tomcat-서버-설정)
4. [프로젝트 구조 및 설정](#4-프로젝트-구조-및-설정)
5. [첫 번째 서블릿 작성](#5-첫-번째-서블릿-작성)
6. [실행 구성 설정](#6-실행-구성-설정)
7. [라이브러리 의존성 추가](#7-라이브러리-의존성-추가)
8. [문제 해결](#8-문제-해결)

---

## 1. 사전 준비

### 📌 필요한 소프트웨어
- **JDK 11** (Oracle JDK 또는 OpenJDK)
- **IntelliJ IDEA** (Community 또는 Ultimate)
- **Apache Tomcat 9.x** (JDK 11 호환)

### 🔧 JDK 11 설치 확인
```bash
# 터미널에서 JDK 버전 확인
java -version
javac -version
```

예상 출력:
```
java version "11.0.x" 
Java(TM) SE Runtime Environment (build 11.0.x)
Java HotSpot(TM) 64-Bit Server VM (build 11.0.x)
```

### 📥 Apache Tomcat 9 다운로드
1. https://tomcat.apache.org/download-90.cgi 접속
2. **Core** 섹션에서 운영체제에 맞는 버전 다운로드
   - Windows: `zip` 파일
   - macOS/Linux: `tar.gz` 파일
3. 적절한 경로에 압축 해제 (예: `C:\apache-tomcat-9.0.xx`)

---

## 2. IntelliJ IDEA 프로젝트 생성

### 📌 새 프로젝트 생성 단계

#### Step 1: 프로젝트 생성 시작
1. IntelliJ IDEA 실행
2. `New Project` 클릭 또는 `File` → `New` → `Project`

#### Step 2: 프로젝트 타입 선택
1. 왼쪽 메뉴에서 **`Java Enterprise`** 선택
2. 프로젝트 설정:
   - **Name**: `ServletExample` (원하는 프로젝트명)
   - **Location**: 프로젝트 저장 경로 설정
   - **Language**: `Java`
   - **Build system**: `Maven` (권장) 또는 `Gradle`
   - **JDK**: `11` 선택 (없으면 Add JDK로 추가)
   - **Java EE Version**: `Java EE 8`
   - **Application server**: `Tomcat 9.0.xx` 선택

#### Step 3: 프로젝트 템플릿 선택
1. **Specifications** 섹션에서 다음 체크:
   - ✅ **Servlet**
   - ✅ **JSP** (선택사항)
2. **Dependencies** 탭에서 필요시 추가 라이브러리 선택
3. `Create` 버튼 클릭

---

## 3. Tomcat 서버 설정

### 📌 IntelliJ에서 Tomcat 설정

#### Step 1: Application Server 추가
1. `File` → `Settings` (Windows/Linux) 또는 `IntelliJ IDEA` → `Preferences` (macOS)
2. `Build, Execution, Deployment` → `Application Servers`
3. `+` 버튼 클릭 → `Tomcat Server` 선택
4. **Tomcat Home**: Tomcat 설치 경로 선택
5. **Name**: `Tomcat 9` (적절한 이름)
6. `OK` 버튼 클릭

#### Step 2: 서버 설정 확인
- **Tomcat Home**: `/path/to/apache-tomcat-9.0.xx`
- **Version**: 자동으로 감지됨
- **JRE**: JDK 11 경로 설정

---

## 4. 프로젝트 구조 및 설정

### 📁 Maven 기반 프로젝트 구조
```
ServletExample/
├── pom.xml                     # Maven 설정 파일
├── src/
│   └── main/
│       ├── java/              # Java 소스 코드
│       │   └── com/
│       │       └── example/
│       │           └── servlet/
│       └── webapp/            # 웹 리소스
│           ├── WEB-INF/
│           │   └── web.xml    # 웹 애플리케이션 설정
│           ├── index.jsp      # 기본 JSP 페이지
│           ├── css/           # CSS 파일
│           └── js/            # JavaScript 파일
└── target/                    # 컴파일된 파일들
```

### 📄 pom.xml 설정 (Maven)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>servlet-example</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <failOnMissingWebXml>false</failOnMissingWebXml>
    </properties>

    <dependencies>
        <!-- Servlet API -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
        
        <!-- JSP API (선택사항) -->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.3</version>
            <scope>provided</scope>
        </dependency>
        
        <!-- JSTL (선택사항) -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Maven Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>
            
            <!-- Maven WAR Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.2.3</version>
                <configuration>
                    <warSourceDirectory>src/main/webapp</warSourceDirectory>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 📄 web.xml 설정 (JDK 11 호환)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
         http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <display-name>Servlet Example</display-name>
    
    <!-- 기본 페이지 설정 -->
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
        <welcome-file>index.html</welcome-file>
    </welcome-file-list>
    
    <!-- 세션 타임아웃 설정 (분 단위) -->
    <session-config>
        <session-timeout>30</session-timeout>
    </session-config>
    
    <!-- 문자 인코딩 필터 -->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.apache.catalina.filters.SetCharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    
</web-app>
```

---

## 5. 첫 번째 서블릿 작성

### 📌 패키지 및 클래스 생성

#### Step 1: 패키지 생성
1. `src/main/java` 우클릭
2. `New` → `Package`
3. 패키지명 입력: `com.example.servlet`

#### Step 2: 서블릿 클래스 생성
```java
package com.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 응답 타입 및 인코딩 설정
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // 출력 스트림 가져오기
        PrintWriter out = response.getWriter();
        
        // HTML 응답 작성
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("    <title>Hello Servlet</title>");
        out.println("    <meta charset='UTF-8'>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <h1>안녕하세요! JDK 11 서블릿입니다.</h1>");
        out.println("    <p>현재 시간: " + new java.util.Date() + "</p>");
        out.println("    <p>Java 버전: " + System.getProperty("java.version") + "</p>");
        out.println("    <p><a href='index.jsp'>홈으로 돌아가기</a></p>");
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
```

### 📄 index.jsp 수정
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>서블릿 예제 홈</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .container { max-width: 600px; margin: 0 auto; }
        .link-box { 
            border: 1px solid #ddd; 
            padding: 20px; 
            margin: 10px 0; 
            border-radius: 5px; 
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🚀 서블릿 예제 프로젝트</h1>
        <p>JDK 11 기반 서블릿 개발 환경이 성공적으로 설정되었습니다!</p>
        
        <div class="link-box">
            <h3>🔗 테스트 링크</h3>
            <ul>
                <li><a href="hello">Hello 서블릿 테스트</a></li>
                <li><a href="form.jsp">폼 데이터 처리 예제</a></li>
                <li><a href="session-test">세션 테스트</a></li>
            </ul>
        </div>
        
        <div class="link-box">
            <h3>📊 시스템 정보</h3>
            <p>현재 시간: <%= new java.util.Date() %></p>
            <p>Java 버전: <%= System.getProperty("java.version") %></p>
            <p>서버 정보: <%= application.getServerInfo() %></p>
        </div>
    </div>
</body>
</html>
```

---

## 6. 실행 구성 설정

### 📌 Tomcat 실행 구성 추가

#### Step 1: 실행 구성 생성
1. 상단 메뉴 `Run` → `Edit Configurations...`
2. 좌측 상단 `+` 버튼 클릭
3. `Tomcat Server` → `Local` 선택

#### Step 2: 서버 설정
**Server 탭**:
- **Name**: `Tomcat 9 - ServletExample`
- **Application server**: 설정한 Tomcat 9 서버 선택
- **JRE**: JDK 11 선택
- **HTTP port**: `8080` (기본값)
- **JMX port**: `1099` (기본값)

**Deployment 탭**:
1. `+` 버튼 클릭 → `Artifact...` 선택
2. `ServletExample:war exploded` 선택
3. **Application context**: `/` 또는 `/ServletExample`

#### Step 3: 실행 및 테스트
1. `Apply` → `OK` 버튼 클릭
2. 상단 초록색 실행 버튼 클릭
3. 브라우저에서 `http://localhost:8080/hello` 접속 테스트

---

## 7. 라이브러리 의존성 추가

### 📌 추가 라이브러리 (선택사항)

#### 데이터베이스 연동 (MySQL)
```xml
<!-- MySQL Connector -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

#### JSON 처리
```xml
<!-- Jackson JSON -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>
```

#### 로깅
```xml
<!-- SLF4J + Logback -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.36</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.12</version>
</dependency>
```

---

## 8. 문제 해결

### 🚨 자주 발생하는 문제들

#### 문제 1: JDK 버전 불일치
**증상**: 컴파일 에러 또는 실행 불가
**해결**:
1. `File` → `Project Structure` → `Project`
   - **Project SDK**: JDK 11 설정
   - **Project language level**: 11 설정
2. `File` → `Project Structure` → `Modules` → `Sources`
   - **Language level**: 11 설정

#### 문제 2: Tomcat 연결 실패
**증상**: 서버 시작 실패 또는 404 에러
**해결**:
1. Tomcat 경로 재확인
2. 포트 충돌 확인 (`netstat -an | findstr 8080`)
3. 방화벽 설정 확인

#### 문제 3: 한글 깨짐 현상
**증상**: 한글이 물음표나 깨진 문자로 표시
**해결**:
```java
// 서블릿에서
request.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=UTF-8");
response.setCharacterEncoding("UTF-8");
```

#### 문제 4: 클래스 찾을 수 없음 에러
**증상**: `ClassNotFoundException` 발생
**해결**:
1. Maven 의존성 새로고침: `View` → `Tool Windows` → `Maven` → 새로고침 버튼
2. 프로젝트 재빌드: `Build` → `Rebuild Project`
3. 캐시 정리: `File` → `Invalidate Caches and Restart`

### ✅ 환경 설정 확인 체크리스트

- [ ] JDK 11 설치 및 설정 완료
- [ ] Apache Tomcat 9 다운로드 및 설정 완료  
- [ ] IntelliJ IDEA에서 JDK 11 프로젝트 생성
- [ ] Maven 의존성 정상 다운로드
- [ ] Tomcat 서버 정상 시작
- [ ] Hello 서블릿 정상 실행
- [ ] 한글 인코딩 정상 처리

---

## 🎯 다음 단계

환경 설정이 완료되면 다음과 같은 학습을 진행할 수 있습니다:

1. **기본 서블릿 작성** - GET/POST 요청 처리
2. **폼 데이터 처리** - 사용자 입력 처리
3. **세션 및 쿠키** - 상태 관리
4. **데이터베이스 연동** - JDBC 사용
5. **JSP와 연동** - MVC 패턴 구현

축하합니다! 🎉 JDK 11 기반 서블릿 개발환경 설정이 완료되었습니다.