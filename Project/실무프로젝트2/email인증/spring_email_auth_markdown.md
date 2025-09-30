# Spring Boot 이메일 인증 시스템 (만료시간 5분)

## 📁 프로젝트 구조

```
src/main/java/com/example/auth/
├── config/
│   └── RedisConfig.java
├── controller/
│   └── EmailController.java
├── dto/
│   ├── EmailRequest.java
│   └── VerifyRequest.java
├── entity/
│   └── VerificationCode.java
├── repository/
│   └── VerificationCodeRepository.java
└── service/
    └── EmailService.java

src/main/resources/
└── application.yml
```

---

## 1️⃣ build.gradle

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-mail'
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

---

## 2️⃣ application.yml

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your-email@gmail.com
    password: your-app-password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            
  redis:
    host: localhost
    port: 6379
    
server:
  port: 8080
```

---

## 3️⃣ Entity: VerificationCode.java

```java
package com.example.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("verificationCode")
public class VerificationCode {
    
    @Id
    private String email;
    
    private String code;
    
    private LocalDateTime createdAt;
    
    private int attempts;
    
    // TTL: 5분 (300초)
    @TimeToLive
    private Long expiration = 300L;
    
    public void incrementAttempts() {
        this.attempts++;
    }
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(createdAt.plusMinutes(5));
    }
    
    public long getRemainingSeconds() {
        LocalDateTime expiryTime = createdAt.plusMinutes(5);
        long seconds = java.time.Duration.between(LocalDateTime.now(), expiryTime).getSeconds();
        return Math.max(0, seconds);
    }
}
```

---

## 4️⃣ Repository: VerificationCodeRepository.java

```java
package com.example.auth.repository;

import com.example.auth.entity.VerificationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
    Optional<VerificationCode> findByEmail(String email);
}
```

---

## 5️⃣ Service: EmailService.java

```java
package com.example.auth.service;

import com.example.auth.entity.VerificationCode;
import com.example.auth.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;
    private final VerificationCodeRepository verificationCodeRepository;
    
    private static final int CODE_LENGTH = 6;
    private static final int MAX_ATTEMPTS = 5;
    
    /**
     * 인증 코드 생성 (6자리 숫자)
     */
    private String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
    
    /**
     * 인증 이메일 발송
     * - Redis에 인증 코드 저장 (5분 TTL)
     */
    public void sendVerificationEmail(String email) throws MessagingException {
        // 인증 코드 생성
        String code = generateCode();
        
        // Redis에 저장 (5분 후 자동 삭제)
        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(code)
                .createdAt(LocalDateTime.now())
                .attempts(0)
                .expiration(300L) // 5분 = 300초
                .build();
        
        verificationCodeRepository.save(verificationCode);
        
        // 이메일 발송
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setTo(email);
        helper.setSubject("[인증코드] 이메일 인증을 완료해주세요");
        helper.setText(createEmailContent(code, email), true);
        
        mailSender.send(message);
        
        log.info("인증 코드 발송 완료: {} (만료시간: 5분)", email);
    }
    
    /**
     * 인증 코드 검증
     */
    public boolean verifyCode(String email, String inputCode) {
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("인증 요청 기록이 없습니다."));
        
        // 최대 시도 횟수 체크
        if (verificationCode.getAttempts() >= MAX_ATTEMPTS) {
            verificationCodeRepository.delete(verificationCode);
            throw new IllegalStateException("인증 시도 횟수를 초과했습니다. 새로운 인증 코드를 요청하세요.");
        }
        
        // 만료 시간 체크
        if (verificationCode.isExpired()) {
            verificationCodeRepository.delete(verificationCode);
            throw new IllegalStateException("인증 코드가 만료되었습니다. (유효시간: 5분)");
        }
        
        // 시도 횟수 증가
        verificationCode.incrementAttempts();
        verificationCodeRepository.save(verificationCode);
        
        // 코드 검증
        if (verificationCode.getCode().equals(inputCode)) {
            verificationCodeRepository.delete(verificationCode);
            log.info("이메일 인증 성공: {}", email);
            return true;
        } else {
            int remainingAttempts = MAX_ATTEMPTS - verificationCode.getAttempts();
            log.warn("인증 코드 불일치: {} (남은 시도: {}회)", email, remainingAttempts);
            return false;
        }
    }
    
    /**
     * 남은 만료 시간 조회 (초 단위)
     */
    public Long getRemainingTime(String email) {
        return verificationCodeRepository.findByEmail(email)
                .map(VerificationCode::getRemainingSeconds)
                .orElse(0L);
    }
    
    /**
     * HTML 이메일 템플릿
     */
    private String createEmailContent(String code, String email) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
            </head>
            <body style="font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;">
                <div style="max-width: 600px; margin: 40px auto; background-color: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <div style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; padding: 30px; text-align: center;">
                        <h1 style="margin: 0; font-size: 28px;">🔐 이메일 인증</h1>
                    </div>
                    <div style="padding: 40px 30px;">
                        <h2>안녕하세요!</h2>
                        <p>%s 계정의 이메일 인증을 진행해주세요.</p>
                        <p>아래 인증 코드를 입력하여 회원가입을 완료하세요.</p>
                        
                        <div style="background-color: #f8f9fa; border: 2px dashed #667eea; border-radius: 8px; padding: 20px; text-align: center; margin: 30px 0;">
                            <div style="color: #6c757d; margin-bottom: 10px;">인증 코드</div>
                            <div style="font-size: 36px; font-weight: bold; color: #667eea; letter-spacing: 8px; font-family: 'Courier New', monospace;">%s</div>
                        </div>
                        
                        <div style="background-color: #fff3cd; border-left: 4px solid #ffc107; padding: 15px; margin: 20px 0; border-radius: 4px;">
                            <strong>⏰ 주의사항</strong><br>
                            • 이 인증 코드는 <strong>5분</strong> 동안만 유효합니다.<br>
                            • 인증 코드를 타인과 공유하지 마세요.<br>
                            • 본인이 요청하지 않았다면 이 이메일을 무시하세요.
                        </div>
                    </div>
                    <div style="background-color: #f8f9fa; padding: 20px; text-align: center; color: #6c757d; font-size: 14px;">
                        <p>이 이메일은 자동으로 발송되었습니다.</p>
                        <p>© 2025 Your Company. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """, email, code);
    }
}
```

---

## 6️⃣ Controller: EmailController.java

```java
package com.example.auth.controller;

import com.example.auth.dto.EmailRequest;
import com.example.auth.dto.VerifyRequest;
import com.example.auth.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmailController {
    
    private final EmailService emailService;
    
    /**
     * 인증 코드 발송
     */
    @PostMapping("/send-code")
    public ResponseEntity<?> sendVerificationCode(@RequestBody EmailRequest request) {
        try {
            emailService.sendVerificationEmail(request.getEmail());
            return ResponseEntity.ok(Map.of(
                "message", "인증 코드가 발송되었습니다.",
                "expirationMinutes", 5
            ));
        } catch (MessagingException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "이메일 발송에 실패했습니다."
            ));
        }
    }
    
    /**
     * 인증 코드 검증
     */
    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyRequest request) {
        try {
            boolean isValid = emailService.verifyCode(request.getEmail(), request.getCode());
            if (isValid) {
                return ResponseEntity.ok(Map.of(
                    "message", "이메일 인증이 완료되었습니다.",
                    "verified", true
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "message", "인증 코드가 일치하지 않습니다.",
                    "verified", false
                ));
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    /**
     * 남은 만료 시간 조회
     */
    @GetMapping("/remaining-time/{email}")
    public ResponseEntity<?> getRemainingTime(@PathVariable String email) {
        Long remainingSeconds = emailService.getRemainingTime(email);
        return ResponseEntity.ok(Map.of(
            "email", email,
            "remainingSeconds", remainingSeconds,
            "remainingMinutes", remainingSeconds / 60
        ));
    }
}
```

---

## 7️⃣ DTO: EmailRequest.java

```java
package com.example.auth.dto;

import lombok.Data;

@Data
public class EmailRequest {
    private String email;
}
```

---

## 8️⃣ DTO: VerifyRequest.java

```java
package com.example.auth.dto;

import lombok.Data;

@Data
public class VerifyRequest {
    private String email;
    private String code;
}
```

---

## 9️⃣ Config: RedisConfig.java

```java
package com.example.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
```

---

## 🚀 API 사용 예시

### 1. 인증 코드 발송
```bash
curl -X POST http://localhost:8080/api/auth/send-code \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com"}'
```

**응답:**
```json
{
  "message": "인증 코드가 발송되었습니다.",
  "expirationMinutes": 5
}
```

### 2. 인증 코드 검증
```bash
curl -X POST http://localhost:8080/api/auth/verify-code \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "code": "123456"}'
```

**응답 (성공):**
```json
{
  "message": "이메일 인증이 완료되었습니다.",
  "verified": true
}
```

### 3. 남은 시간 조회
```bash
curl http://localhost:8080/api/auth/remaining-time/user@example.com
```

**응답:**
```json
{
  "email": "user@example.com",
  "remainingSeconds": 243,
  "remainingMinutes": 4
}
```

---

## ⚙️ 설정 방법

### 1. Redis 설치 및 실행

**macOS:**
```bash
brew install redis
brew services start redis
```

**Windows:**
- https://github.com/microsoftarchive/redis/releases 에서 다운로드

**Docker:**
```bash
docker run -d -p 6379:6379 redis
```

### 2. Gmail 앱 비밀번호 생성
1. Google 계정 관리 → 보안
2. 2단계 인증 활성화
3. 앱 비밀번호 생성
4. `application.yml`에 입력

### 3. 애플리케이션 실행
```bash
./gradlew bootRun
```

---

## 📌 주요 기능

✅ **인증 코드 5분 만료** - Redis TTL 자동 관리  
✅ **최대 5회 시도 제한** - 보안 강화  
✅ **실시간 남은 시간 조회** - 사용자 편의성  
✅ **HTML 이메일 템플릿** - 전문적인 디자인  
✅ **예외 처리** - 명확한 에러 메시지

---

## 📝 라이센스

MIT License

---

## 👨‍💻 작성자

Spring Boot Email Authentication System
