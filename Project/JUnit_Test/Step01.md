# Junit 테스트 5단계 학습 가이드 - Spring Boot 웹개발 기반

## 1단계: Spring Boot 환경에서 Junit 기본 개념과 첫 번째 테스트

### 이론 부분

**Spring Boot 테스트 환경**
- Spring Boot Test: 스프링 부트 애플리케이션 테스트를 위한 통합 지원
- `@SpringBootTest`: 전체 애플리케이션 컨텍스트를 로드하는 통합 테스트
- `@WebMvcTest`: 웹 레이어만 테스트 (Controller 테스트에 특화)
- `@DataJpaTest`: JPA 관련 컴포넌트만 테스트

**의존성 구조**
```
spring-boot-starter-web
└── spring-boot-starter-test
    ├── junit-jupiter
    ├── mockito
    └── assertj
```

### 실습 부분

**의존성 추가 (Maven)**
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

**회원 도메인 엔티티 생성**
```java
// src/main/java/com/example/member/domain/Member.java
package com.example.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String password;
    
    private Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
    
    public static Member createMember(String email, String name, String password) {
        return new Member(email, name, password);
    }
    
    public void updateName(String name) {
        this.name = name;
    }
}
```

**회원 Repository**
```java
// src/main/java/com.example.member/repository/MemberRepository.java
package com.example.member.repository;

import com.example.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

**회원 Service**
```java
// src/main/java/com/example/member/service/MemberService.java
package com.example.member.service;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;
    
    public Long register(String email, String name, String password) {
        validateDuplicateEmail(email);
        
        Member member = Member.createMember(email, name, password);
        Member savedMember = memberRepository.save(member);
        
        return savedMember.getId();
    }
    
    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }
    
    @Transactional(readOnly = true)
    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }
    
    public void updateName(Long id, String name) {
        Member member = findMember(id);
        member.updateName(name);
    }
}
```

**기본 테스트 클래스 작성**
```java
// src/test/java/com/example/member/MemberDomainTest.java
package com.example.member;

import com.example.member.domain.Member;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemberDomainTest {

    @Test
    void 회원_생성_테스트() {
        // Given
        String email = "test@example.com";
        String name = "홍길동";
        String password = "password123";
        
        // When
        Member member = Member.createMember(email, name, password);
        
        // Then
        assertNotNull(member);
        assertEquals(email, member.getEmail());
        assertEquals(name, member.getName());
        assertEquals(password, member.getPassword());
    }
    
    @Test
    void 회원_이름_수정_테스트() {
        // Given
        Member member = Member.createMember("test@example.com", "기존이름", "password");
        
        // When
        member.updateName("새로운이름");
        
        // Then
        assertEquals("새로운이름", member.getName());
    }
}
```

**Service 단위 테스트**
```java
// src/test/java/com/example/member/service/MemberServiceTest.java
package com.example.member.service;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 회원_등록_성공() {
        // Given
        String email = "test@example.com";
        String name = "홍길동";
        String password = "password123";
        
        when(memberRepository.existsByEmail(email)).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(Member.createMember(email, name, password));

        // When
        Long memberId = memberService.register(email, name, password);

        // Then
        assertNotNull(memberId);
        verify(memberRepository, times(1)).existsByEmail(email);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void 중복_이메일_회원_등록_실패() {
        // Given
        String email = "duplicate@example.com";
        when(memberRepository.existsByEmail(email)).thenReturn(true);

        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            memberService.register(email, "홍길동", "password");
        });
    }

    @Test
    void 회원_조회_성공() {
        // Given
        Long memberId = 1L;
        Member expectedMember = Member.createMember("test@example.com", "홍길동", "password");
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(expectedMember));

        // When
        Member foundMember = memberService.findMember(memberId);

        // Then
        assertNotNull(foundMember);
        assertEquals(expectedMember.getEmail(), foundMember.getEmail());
    }
}
```

**통합 테스트**
```java
// src/test/java/com/example/member/MemberIntegrationTest.java
package com.example.member;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberIntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원_저장_및_조회_통합_테스트() {
        // Given
        Member member = Member.createMember("integration@test.com", "통합테스트", "password");

        // When
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMember.getId()).orElse(null);

        // Then
        assertNotNull(foundMember);
        assertEquals(member.getEmail(), foundMember.getEmail());
        assertEquals(member.getName(), foundMember.getName());
    }

    @Test
    void 이메일_중복_체크_테스트() {
        // Given
        String email = "duplicate@test.com";
        Member member = Member.createMember(email, "중복체크", "password");
        memberRepository.save(member);

        // When
        boolean exists = memberRepository.existsByEmail(email);

        // Then
        assertTrue(exists);
    }
}
```

### 연습 문제

1. **회원 비밀번호 변경 기능 추가 및 테스트 작성**
   - `Member` 엔티티에 `updatePassword` 메소드 추가
   - 해당 기능을 테스트하는 Junit 테스트 작성

2. **회원 등록 시 유효성 검증 테스트**
   - 이메일 형식 검증 테스트
   - 이름 길이 제한 테스트 작성

3. **Repository 커스텀 메소드 테스트**
   - 이름으로 회원 찾기 메소드 추가 및 테스트

**다음 단계에서는 Spring Boot 테스트의 다양한 어노테이션과 Mockito를 활용한 테스트 방법을 배워보겠습니다!**
