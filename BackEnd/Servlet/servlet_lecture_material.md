# 자바 서블릿(Java Servlet) 강의자료

## 📋 목차
1. [서블릿이란?](#1-서블릿이란)
2. [서블릿 생명주기](#2-서블릿-생명주기)
3. [첫 번째 서블릿 만들기](#3-첫-번째-서블릿-만들기)
4. [HTTP 요청/응답 처리](#4-http-요청응답-처리)
5. [폼 데이터 처리](#5-폼-데이터-처리)
6. [세션 관리](#6-세션-관리)
7. [쿠키 사용법](#7-쿠키-사용법)
8. [실습 과제](#8-실습-과제)

---

## 1. 서블릿이란?

### 📌 핵심 개념
- **서블릿(Servlet)**: 웹 서버에서 실행되는 자바 프로그램
- **목적**: 동적 웹 페이지 생성, 클라이언트 요청 처리
- **특징**: 플랫폼 독립적, 멀티스레딩 지원, 재사용 가능

### 🔄 웹 서버와 서블릿의 관계
```
클라이언트(브라우저) → HTTP 요청 → 웹 서버 → 서블릿 컨테이너 → 서블릿
클라이언트(브라우저) ← HTTP 응답 ← 웹 서버 ← 서블릿 컨테이너 ← 서블릿
```

---

## 2. 서블릿 생명주기

### 📌 핵심 메서드
1. **init()**: 서블릿 초기화 (한 번만 실행)
2. **service()**: 요청 처리 (요청마다 실행)
3. **destroy()**: 서블릿 종료 (한 번만 실행)

### 💡 예제: 생명주기 확인
```java
@WebServlet("/lifecycle")
public class LifecycleServlet extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        System.out.println("1. init() 호출 - 서블릿 초기화");
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("2. service() 호출 - 요청 처리");
        super.service(request, response); // doGet 또는 doPost 호출
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("3. doGet() 호출");
        response.getWriter().println("<h1>생명주기 테스트</h1>");
    }
    
    @Override
    public void destroy() {
        System.out.println("4. destroy() 호출 - 서블릿 종료");
    }
}
```

---

## 3. 첫 번째 서블릿 만들기

### 📌 기본 구조
```java
@WebServlet("/hello")  // URL 매핑
public class HelloServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. 응답 타입 설정
        response.setContentType("text/html;charset=UTF-8");
        
        // 2. 출력 스트림 가져오기
        PrintWriter out = response.getWriter();
        
        // 3. HTML 응답 작성
        out.println("<html><body>");
        out.println("<h1>안녕하세요!</h1>");
        out.println("<p>첫 번째 서블릿입니다.</p>");
        out.println("</body></html>");
    }
}
```

### 🎯 핵심 포인트
- `@WebServlet` 어노테이션으로 URL 매핑
- `HttpServlet` 상속 필수
- `doGet()` 또는 `doPost()` 메서드 오버라이드
- `response.setContentType()` 으로 응답 타입 설정

---

## 4. HTTP 요청/응답 처리

### 📌 HTTP 메서드별 처리

#### GET 요청 처리
```java
@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // URL 파라미터 받기: /student?id=123&name=홍길동
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<h1>학생 정보</h1>");
        out.println("<p>ID: " + id + "</p>");
        out.println("<p>이름: " + name + "</p>");
    }
}
```

#### POST 요청 처리
```java
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    // 한글 인코딩 설정 (POST에서 중요!)
    request.setCharacterEncoding("UTF-8");
    
    // 폼 데이터 받기
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    // 처리 로직...
    if ("admin".equals(username) && "1234".equals(password)) {
        response.getWriter().println("<h1>로그인 성공!</h1>");
    } else {
        response.getWriter().println("<h1>로그인 실패!</h1>");
    }
}
```

---

## 5. 폼 데이터 처리

### 📌 HTML 폼 예제
```html
<!-- login.html -->
<form action="login" method="post">
    <input type="text" name="username" placeholder="사용자명" required>
    <input type="password" name="password" placeholder="비밀번호" required>
    <input type="submit" value="로그인">
</form>
```

### 💡 완전한 로그인 서블릿
```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 로그인 폼 표시
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>로그인</h2>");
        out.println("<form action='login' method='post'>");
        out.println("사용자명: <input type='text' name='username'><br><br>");
        out.println("비밀번호: <input type='password' name='password'><br><br>");
        out.println("<input type='submit' value='로그인'>");
        out.println("</form>");
        out.println("</body></html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        
        // 간단한 인증 로직
        if ("student".equals(username) && "1234".equals(password)) {
            out.println("<h1>환영합니다, " + username + "님!</h1>");
            out.println("<p>로그인에 성공했습니다.</p>");
        } else {
            out.println("<h1>로그인 실패</h1>");
            out.println("<p>사용자명 또는 비밀번호가 잘못되었습니다.</p>");
            out.println("<a href='login'>다시 시도</a>");
        }
        
        out.println("</body></html>");
    }
}
```

---

## 6. 세션 관리

### 📌 세션의 이해
- **세션**: 서버에서 클라이언트 상태 정보를 유지하는 방법
- **용도**: 로그인 상태 유지, 장바구니, 사용자 설정 등

### 💡 세션 사용 예제
```java
@WebServlet("/session-demo")
public class SessionDemoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // 세션 가져오기 (없으면 새로 생성)
        HttpSession session = request.getSession();
        
        // 방문 횟수 증가
        Integer visitCount = (Integer) session.getAttribute("visitCount");
        if (visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }
        session.setAttribute("visitCount", visitCount);
        
        // 사용자 이름 설정 (처음 방문시)
        String username = (String) session.getAttribute("username");
        if (username == null) {
            username = "방문자" + session.getId().substring(0, 8);
            session.setAttribute("username", username);
        }
        
        out.println("<html><body>");
        out.println("<h1>세션 데모</h1>");
        out.println("<p>안녕하세요, " + username + "님!</p>");
        out.println("<p>방문 횟수: " + visitCount + "</p>");
        out.println("<p>세션 ID: " + session.getId() + "</p>");
        out.println("<p><a href='session-demo'>새로고침</a> | ");
        out.println("<a href='logout'>로그아웃</a></p>");
        out.println("</body></html>");
    }
}
```

### 🔐 로그아웃 서블릿
```java
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h1>로그아웃 완료</h1>");
        out.println("<p>세션이 종료되었습니다.</p>");
        out.println("<p><a href='session-demo'>다시 시작</a></p>");
        out.println("</body></html>");
    }
}
```

---

## 7. 쿠키 사용법

### 📌 쿠키의 이해
- **쿠키**: 클라이언트(브라우저)에 저장되는 작은 데이터
- **용도**: 사용자 선호 설정, 자동 로그인, 최근 방문 기록 등

### 💡 쿠키 예제
```java
@WebServlet("/cookie-demo")
public class CookieDemoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // 쿠키 읽기
        String lastVisit = "첫 방문";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastVisit".equals(cookie.getName())) {
                    lastVisit = cookie.getValue();
                    break;
                }
            }
        }
        
        // 새로운 쿠키 생성 (현재 시간)
        String currentTime = new Date().toString();
        Cookie newCookie = new Cookie("lastVisit", currentTime);
        newCookie.setMaxAge(60 * 60 * 24); // 1일 유지
        response.addCookie(newCookie);
        
        out.println("<html><body>");
        out.println("<h1>쿠키 데모</h1>");
        out.println("<p>마지막 방문: " + lastVisit + "</p>");
        out.println("<p>현재 시간: " + currentTime + "</p>");
        out.println("<p><a href='cookie-demo'>새로고침</a></p>");
        out.println("</body></html>");
    }
}
```

---

## 8. 실습 과제

### 🎯 과제 1: 계산기 서블릿
**요구사항**:
- 두 수와 연산자(+, -, *, /)를 입력받는 폼
- 계산 결과를 화면에 표시
- 잘못된 입력에 대한 에러 처리

### 🎯 과제 2: 방문자 카운터
**요구사항**:
- 전체 방문자 수를 서블릿 컨텍스트에 저장
- 개인 방문 횟수를 세션에 저장  
- 페이지 새로고침할 때마다 카운터 증가

### 🎯 과제 3: 간단한 게시판
**요구사항**:
- 글 작성 폼 (제목, 내용, 작성자)
- 글 목록 보기
- ArrayList를 사용하여 메모리에 저장

---

## 📚 핵심 정리

### ✅ 반드시 기억해야 할 것들

1. **서블릿 기본 구조**
   ```java
   @WebServlet("/url")
   public class MyServlet extends HttpServlet {
       protected void doGet/doPost(request, response) { ... }
   }
   ```

2. **요청 파라미터 받기**
   ```java
   String value = request.getParameter("paramName");
   ```

3. **응답 보내기**
   ```java
   response.setContentType("text/html;charset=UTF-8");
   PrintWriter out = response.getWriter();
   out.println("HTML 내용");
   ```

4. **세션 사용**
   ```java
   HttpSession session = request.getSession();
   session.setAttribute("key", value);
   Object value = session.getAttribute("key");
   ```

5. **인코딩 설정** (한글 처리)
   ```java
   request.setCharacterEncoding("UTF-8");  // POST 요청
   response.setContentType("text/html;charset=UTF-8");  // 응답
   ```

### 🚨 주의사항
- POST 요청에서는 반드시 `request.setCharacterEncoding("UTF-8")` 설정
- 세션 사용 전 null 체크 필수
- 서블릿은 멀티스레드 환경에서 실행됨 (인스턴스 변수 사용 주의)

---

## 🔗 다음 단계 학습
1. **JSP (JavaServer Pages)** - 뷰 레이어 분리
2. **MVC 패턴** - 모델-뷰-컨트롤러 구조
3. **필터(Filter)** - 요청/응답 전처리
4. **리스너(Listener)** - 이벤트 처리
5. **Spring Framework** - 현대적 웹 프레임워크