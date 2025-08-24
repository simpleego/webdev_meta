# 2단계
> Thymeleaf를 활용해서 서버에서 전달된 데이터를 웹 화면에 출력하는 간단한 예제
> MVC 흐름을 이해하고, 동적인 데이터를 HTML에 표시하는 방법을 안다.

---

## 🧪 예제: 사용자 이름을 화면에 출력하기

### 📁 디렉토리 구조
```
src/
 └─ main/
     ├─ java/com/example/demo/
     │   └─ controller/GreetingController.java
     └─ resources/templates/greeting.html
```

---

### 1️⃣ Controller 클래스 작성

```java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(Model model) {
        model.addAttribute("name", "홍길동");
        return "greeting"; // templates/greeting.html을 렌더링
    }
}
```

- `Model` 객체를 통해 데이터를 전달
- `name`이라는 키로 `"홍길동"` 값을 전달

---

### 2️⃣ Thymeleaf 템플릿 작성 (`resources/templates/greeting.html`)

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>인사 페이지</title>
</head>
<body>
    <h1>안녕하세요, <span th:text="${name}">이름</span>님!</h1>
</body>
</html>
```

- `${name}`은 서버에서 전달된 데이터를 의미
- `th:text`는 해당 태그의 텍스트를 동적으로 변경

---

### 3️⃣ 실행 및 확인

- 애플리케이션 실행 후 브라우저에서 `http://localhost:8080/greeting` 접속
- 화면에 `안녕하세요, 홍길동님!` 출력됨

---

### 🔧 확장 아이디어

- URL 파라미터로 이름을 전달받기:
```java
@GetMapping("/greeting")
public String greeting(@RequestParam(name = "name", defaultValue = "손님") String name, Model model) {
    model.addAttribute("name", name);
    return "greeting";
}
```
- URL 예시: `http://localhost:8080/greeting?name=철수`

---
