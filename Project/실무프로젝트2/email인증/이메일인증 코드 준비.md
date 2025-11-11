# 스프링부트 이메일 인증 회원가입 전체 프로젝트

## 📁 프로젝트 구조
```
email-verification-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── demo/
│   │   │               ├── DemoApplication.java
│   │   │               ├── controller/
│   │   │               │   └── EmailVerificationController.java
│   │   │               └── service/
│   │   │                   └── EmailService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/
│   │           └── signup.html
└── pom.xml
```

---

## 📄 pom.xml
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
    
    <groupId>com.example</groupId>
    <artifactId>email-verification-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>email-verification-demo</name>
    <description>Email verification demo project</description>
    
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
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

---

## 📄 DemoApplication.java
```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

---

## 📄 EmailVerificationController.java
```java
package com.example.demo.controller;

import com.example.demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EmailVerificationController {
    
    private final EmailService emailService;
    
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }
    
    @PostMapping("/send-code")
    @ResponseBody
    public String sendVerificationCode(@RequestParam String email) {
        try {
            emailService.sendVerificationCode(email);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }
    
    @PostMapping("/verify-code")
    @ResponseBody
    public String verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = emailService.verifyCode(email, code);
        return isValid ? "success" : "fail";
    }
    
    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestParam String email, 
                        @RequestParam String password,
                        @RequestParam String name) {
        // TODO: 실제 회원가입 로직 구현 (DB 저장 등)
        return "회원가입 완료: " + name + " (" + email + ")";
    }
}
```

---

## 📄 EmailService.java
```java
package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    // 실제 운영환경에서는 Redis 등을 사용하세요
    private final Map<String, String> verificationCodes = new HashMap<>();
    
    public void sendVerificationCode(String email) {
        String code = generateCode();
        verificationCodes.put(email, code);
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("회원가입 인증 코드");
        message.setText("인증 코드: " + code + "\n\n5분 내에 입력해주세요.");
        
        mailSender.send(message);
    }
    
    public boolean verifyCode(String email, String code) {
        String savedCode = verificationCodes.get(email);
        if (savedCode != null && savedCode.equals(code)) {
            verificationCodes.remove(email);
            return true;
        }
        return false;
    }
    
    private String generateCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}
```

---

## 📄 application.properties
```properties
# 서버 포트
server.port=8080

# Gmail SMTP 설정
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

# Thymeleaf 설정
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false
```

---

## 📄 signup.html
```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        .container {
            background: white;
            border-radius: 10px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            padding: 40px;
            width: 100%;
            max-width: 450px;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
            font-size: 28px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-weight: 500;
        }
        input {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.3s;
        }
        input:focus {
            outline: none;
            border-color: #667eea;
        }
        .button-group {
            display: flex;
            gap: 10px;
        }
        button {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        .btn-primary {
            background: #667eea;
            color: white;
        }
        .btn-primary:hover {
            background: #5568d3;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        .btn-secondary {
            background: #48bb78;
            color: white;
        }
        .btn-secondary:hover {
            background: #38a169;
        }
        .btn-verify {
            background: #ed8936;
            color: white;
        }
        .btn-verify:hover {
            background: #dd6b20;
        }
        button:disabled {
            background: #cbd5e0;
            cursor: not-allowed;
            transform: none;
        }
        .message {
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 6px;
            font-size: 14px;
            text-align: center;
            display: none;
        }
        .message.show {
            display: block;
        }
        .success {
            background-color: #c6f6d5;
            color: #22543d;
            border-left: 4px solid #38a169;
        }
        .error {
            background-color: #fed7d7;
            color: #742a2a;
            border-left: 4px solid #e53e3e;
        }
        .verified-badge {
            display: inline-block;
            background: #38a169;
            color: white;
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 12px;
            margin-left: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>🔐 회원가입</h2>
        
        <div id="message" class="message"></div>
        
        <form id="signupForm">
            <div class="form-group">
                <label for="name">이름</label>
                <input type="text" id="name" placeholder="홍길동" required>
            </div>
            
            <div class="form-group">
                <label for="email">
                    이메일
                    <span id="verifiedBadge" style="display:none;" class="verified-badge">✓ 인증완료</span>
                </label>
                <div class="button-group">
                    <input type="email" id="email" placeholder="example@email.com" required style="flex: 2;">
                    <button type="button" class="btn-secondary" onclick="sendCode()" style="flex: 1;">코드전송</button>
                </div>
            </div>
            
            <div class="form-group">
                <label for="code">인증코드</label>
                <div class="button-group">
                    <input type="text" id="code" placeholder="6자리 숫자" maxlength="6" required style="flex: 2;">
                    <button type="button" class="btn-verify" onclick="verifyCode()" style="flex: 1;">인증하기</button>
                </div>
            </div>
            
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" placeholder="8자 이상" required minlength="8">
            </div>
            
            <button type="submit" class="btn-primary" id="signupBtn" disabled>회원가입</button>
        </form>
    </div>

    <script>
        let isVerified = false;

        function showMessage(msg, isSuccess) {
            const messageDiv = document.getElementById('message');
            messageDiv.textContent = msg;
            messageDiv.className = 'message show ' + (isSuccess ? 'success' : 'error');
            setTimeout(() => {
                messageDiv.classList.remove('show');
            }, 3000);
        }

        function sendCode() {
            const email = document.getElementById('email').value;
            if (!email) {
                showMessage('이메일을 입력하세요', false);
                return;
            }

            // 이메일 형식 검증
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                showMessage('올바른 이메일 형식이 아닙니다', false);
                return;
            }

            showMessage('인증코드 발송 중...', true);

            fetch('/send-code', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: 'email=' + encodeURIComponent(email)
            })
            .then(response => response.text())
            .then(data => {
                if (data === 'success') {
                    showMessage('✉️ 인증코드가 이메일로 발송되었습니다!', true);
                } else {
                    showMessage('발송 실패. 다시 시도해주세요', false);
                }
            })
            .catch(() => showMessage('오류가 발생했습니다', false));
        }

        function verifyCode() {
            const email = document.getElementById('email').value;
            const code = document.getElementById('code').value;
            
            if (!email || !code) {
                showMessage('이메일과 인증코드를 입력하세요', false);
                return;
            }

            if (code.length !== 6) {
                showMessage('인증코드는 6자리 숫자입니다', false);
                return;
            }

            fetch('/verify-code', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: 'email=' + encodeURIComponent(email) + '&code=' + encodeURIComponent(code)
            })
            .then(response => response.text())
            .then(data => {
                if (data === 'success') {
                    isVerified = true;
                    document.getElementById('signupBtn').disabled = false;
                    document.getElementById('verifiedBadge').style.display = 'inline-block';
                    document.getElementById('email').readOnly = true;
                    document.getElementById('code').readOnly = true;
                    showMessage('✅ 인증이 완료되었습니다!', true);
                } else {
                    showMessage('❌ 인증코드가 일치하지 않습니다', false);
                }
            })
            .catch(() => showMessage('오류가 발생했습니다', false));
        }

        document.getElementById('signupForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            if (!isVerified) {
                showMessage('이메일 인증을 완료해주세요', false);
                return;
            }

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const name = document.getElementById('name').value;

            if (password.length < 8) {
                showMessage('비밀번호는 8자 이상이어야 합니다', false);
                return;
            }

            fetch('/signup', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: `email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}&name=${encodeURIComponent(name)}`
            })
            .then(response => response.text())
            .then(data => {
                showMessage('🎉 ' + data, true);
                setTimeout(() => {
                    // 회원가입 후 이동할 페이지 (예: 로그인 페이지)
                    alert('회원가입이 완료되었습니다!');
                    window.location.href = '/';
                }, 2000);
            })
            .catch(() => showMessage('회원가입 실패', false));
        });

        // 엔터키로 다음 단계 진행
        document.getElementById('email').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                sendCode();
            }
        });

        document.getElementById('code').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                verifyCode();
            }
        });
    </script>
</body>
</html>
```

---

## 🚀 실행 방법

### 1. Gmail 설정
1. Google 계정 로그인
2. [Google 계정 관리](https://myaccount.google.com/) → 보안
3. 2단계 인증 활성화
4. 앱 비밀번호 생성
5. `application.properties`에 이메일과 앱 비밀번호 입력

### 2. 프로젝트 실행
```bash
mvn clean install
mvn spring-boot:run
```

### 3. 접속
브라우저에서 `http://localhost:8080/signup` 접속

---

## 📝 주의사항

- **보안**: 운영환경에서는 인증코드를 Redis 등에 저장하고 만료시간 설정 필요
- **비밀번호**: 실제로는 BCrypt 등으로 암호화하여 저장해야 함
- **DB 연동**: 회원 정보를 DB에 저장하는 로직 추가 필요
- **유효성 검사**: 이메일 중복 체크, 비밀번호 강도 검증 등 추가 권장

---

## 📌 개선 사항 (선택사항)

1. 인증코드 만료시간 설정 (5분)
2. 이메일 중복 체크
3. 비밀번호 확인 입력
4. SMTP 서버 변경 (Naver, Kakao 등)
5. HTML 템플릿 이메일 발송
