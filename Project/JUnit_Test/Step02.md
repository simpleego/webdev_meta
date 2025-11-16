# 2단계: Spring Boot 테스트 어노테이션과 Mockito 심화

## 이론 부분

### Spring Boot 테스트 어노테이션

**@SpringBootTest**
- 통합 테스트용, 전체 애플리케이션 컨텍스트를 로드
- `webEnvironment` 속성으로 테스트 환경 설정
  - `WebEnvironment.MOCK`: Mock 서블릿 환경 (기본값)
  - `WebEnvironment.RANDOM_PORT`: 실제 임베디드 서버 실행

**@WebMvcTest**
- 웹 레이어만 테스트, Controller 관련 빈만 로드
- `@MockBean`으로 의존성 주입

**@DataJpaTest**
- JPA 관련 컴포넌트만 테스트
- 기본적으로 인메모리 DB 사용, 트랜잭션 자동 롤백

**@MockBean**
- Spring ApplicationContext에 Mock 객체 주입

### Mockito 심화

**Mockito 주요 기능**
- `when().thenReturn()`: Mock 객체의 행동 정의
- `verify()`: 메소드 호출 검증
- `ArgumentMatchers`: 파라미터 매칭
- `@InjectMocks`: Mock 객체를 주입할 대상 지정

## 실습 부분

### 회원 Controller 추가

```java
// src/main/java/com/example/member/controller/MemberController.java
package com.example.member.controller;

import com.example.member.domain.Member;
import com.example.member.dto.MemberRequest;
import com.example.member.dto.MemberResponse;
import com.example.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> register(@Valid @RequestBody MemberRequest request) {
        Long memberId = memberService.register(request.getEmail(), request.getName(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(new MemberResponse(memberId, "회원 가입 성공"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        Member member = memberService.findMember(id);
        MemberResponse response = MemberResponse.from(member);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<MemberResponse> updateName(@PathVariable Long id, @RequestBody MemberRequest request) {
        memberService.updateName(id, request.getName());
        Member updatedMember = memberService.findMember(id);
        return ResponseEntity.ok(MemberResponse.from(updatedMember));
    }
}
```

### DTO 클래스들 추가

```java
// src/main/java/com/example/member/dto/MemberRequest.java
package com.example.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequest {
    
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하로 입력해주세요.")
    private String name;
    
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 4, message = "비밀번호는 4자 이상 입력해주세요.")
    private String password;

    public MemberRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
```

```java
// src/main/java/com/example/member/dto/MemberResponse.java
package com.example.member.dto;

import com.example.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {
    private Long id;
    private String email;
    private String name;
    private String message;

    public MemberResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public MemberResponse(Long id, String email, String name, String message) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.message = message;
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(
            member.getId(),
            member.getEmail(),
            member.getName(),
            "회원 조회 성공"
        );
    }
}
```

### 1. @WebMvcTest를 이용한 Controller 테스트

```java
// src/test/java/com/example/member/controller/MemberControllerTest.java
package com.example.member.controller;

import com.example.member.domain.Member;
import com.example.member.dto.MemberRequest;
import com.example.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    void 회원_등록_성공() throws Exception {
        // Given
        MemberRequest request = new MemberRequest("test@example.com", "홍길동", "password123");
        when(memberService.register(anyString(), anyString(), anyString())).thenReturn(1L);

        // When & Then
        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("회원 가입 성공"));
    }

    @Test
    void 회원_등록_유효성_검증_실패() throws Exception {
        // Given - 잘못된 이메일 형식
        MemberRequest request = new MemberRequest("invalid-email", "홍", "123");

        // When & Then
        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 회원_조회_성공() throws Exception {
        // Given
        Member member = Member.createMember("test@example.com", "홍길동", "password");
        when(memberService.findMember(anyLong())).thenReturn(member);

        // When & Then
        mockMvc.perform(get("/api/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("홍길동"))
                .andExpect(jsonPath("$.message").value("회원 조회 성공"));
    }

    @Test
    void 회원_이름_수정_성공() throws Exception {
        // Given
        MemberRequest request = new MemberRequest();
        request.setName("새로운이름");
        
        Member updatedMember = Member.createMember("test@example.com", "새로운이름", "password");
        when(memberService.findMember(anyLong())).thenReturn(updatedMember);

        // When & Then
        mockMvc.perform(patch("/api/members/1/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"새로운이름\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("새로운이름"));
    }
}
```

### 2. Mockito 심화 테스트

```java
// src/test/java/com/example/member/service/MemberServiceMockitoTest.java
package com.example.member.service;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceMockitoTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원 등록 - ArgumentMatchers 사용")
    void register_withArgumentMatchers() {
        // Given
        when(memberRepository.existsByEmail(anyString())).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            return Member.createMember(member.getEmail(), member.getName(), member.getPassword());
        });

        // When
        Long memberId = memberService.register("test@example.com", "홍길동", "password123");

        // Then
        assertNotNull(memberId);
        verify(memberRepository).existsByEmail(argThat(email -> email.contains("@")));
        verify(memberRepository).save(argThat(member -> 
            member.getName().equals("홍길동") && member.getEmail().equals("test@example.com")
        ));
    }

    @Test
    @DisplayName("회원 등록 - 중복 이메일 검증")
    void register_duplicateEmail_throwsException() {
        // Given
        String duplicateEmail = "duplicate@example.com";
        when(memberRepository.existsByEmail(eq(duplicateEmail))).thenReturn(true);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> memberService.register(duplicateEmail, "홍길동", "password123"));
        
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
        verify(memberRepository, times(1)).existsByEmail(duplicateEmail);
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 조회 - Optional 처리 테스트")
    void findMember_optionalHandling() {
        // Given
        Long memberId = 1L;
        Member expectedMember = Member.createMember("test@example.com", "홍길동", "password");
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(expectedMember));

        // When
        Member foundMember = memberService.findMember(memberId);

        // Then
        assertNotNull(foundMember);
        assertEquals(expectedMember.getEmail(), foundMember.getEmail());
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("회원 조회 - 존재하지 않는 회원")
    void findMember_notFound_throwsException() {
        // Given
        Long nonExistingId = 999L;
        when(memberRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> memberService.findMember(nonExistingId));
        
        assertEquals("회원을 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원 이름 수정 - verify 호출 횟수 검증")
    void updateName_verifyCallCount() {
        // Given
        Long memberId = 1L;
        String newName = "새로운이름";
        Member existingMember = Member.createMember("test@example.com", "기존이름", "password");
        
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        // When
        memberService.updateName(memberId, newName);

        // Then
        verify(memberRepository, times(1)).findById(memberId);
        // 이름이 실제로 변경되었는지 확인
        assertEquals(newName, existingMember.getName());
    }
}
```

### 3. @SpringBootTest 통합 테스트

```java
// src/test/java/com/example/member/MemberIntegrationTest.java
package com.example.member;

import com.example.member.domain.Member;
import com.example.member.dto.MemberRequest;
import com.example.member.repository.MemberRepository;
import com.example.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class MemberIntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원_전체_생명주기_테스트() {
        // Given
        String email = "integration@test.com";
        String name = "통합테스트";
        String password = "password123";

        // When - 회원 등록
        Long memberId = memberService.register(email, name, password);

        // Then - 회원 조회
        Member foundMember = memberService.findMember(memberId);
        assertNotNull(foundMember);
        assertEquals(email, foundMember.getEmail());
        assertEquals(name, foundMember.getName());

        // When - 이름 수정
        String updatedName = "수정된이름";
        memberService.updateName(memberId, updatedName);

        // Then - 수정 확인
        Member updatedMember = memberService.findMember(memberId);
        assertEquals(updatedName, updatedMember.getName());
    }

    @Test
    void 중복_이메일_검증_통합_테스트() {
        // Given
        String email = "duplicate@test.com";
        memberService.register(email, "첫번째회원", "password1");

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> memberService.register(email, "두번째회원", "password2"));
        
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
    }
}
```

## 실습 문제

1. **회원 Controller 예외 처리 테스트 작성**
   - 존재하지 않는 회원 조회 시 404 응답 테스트
   - 중복 이메일 회원가입 시 400 응답 테스트

2. **Mockito를 이용한 행위 검증 테스트**
   - `verify()`를 사용하여 특정 메소드가 정확한 횟수로 호출되는지 검증
   - `ArgumentCaptor`를 사용하여 메소드에 전달된 인자 캡처 및 검증

3. **@DataJpaTest를 이용한 Repository 테스트**
   - 커스텀 쿼리 메소드 테스트
   - @Query 어노테이션을 사용한 JPQL 쿼리 테스트

**다음 3단계에서는 테스트 전략, Given-When-Then 패턴, 테스트 커버리지 측정에 대해 배워보겠습니다!**
