# 5단계: 테스트 전략 수립, CI/CD 통합, 실제 프로젝트 적용

## 이론 부분

### 테스트 전략 (Test Strategy)

**테스트 전략 구성 요소**
- 테스트 범위와 목표 정의
- 테스트 환경 구성
- 테스트 데이터 관리 전략
- 테스트 자동화 계획
- 품질 기준과 측정 지표

### CI/CD 파이프라인과 테스트

**CI/CD 단계별 테스트**
```
코드 커밋 → 빌드 → 단위 테스트 → 통합 테스트 → E2E 테스트 → 배포
```

### 테스트 커버리지와 품질 게이트

**품질 게이트 조건**
- 테스트 커버리지 최소치
- 테스트 실패율 제한
- 정적 분석 결과
- 성능 기준 충족

## 실습 부분

### 1. 테스트 전략 문서화 및 구성

**테스트 구성 파일 생성**
```java
// src/test/java/com/example/member/config/TestProfileConfig.java
package com.example.member.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import com.example.member.repository.MemberRepository;
import com.example.member.doubles.FakeMemberRepository;

@TestConfiguration
@Profile("test")
public class TestProfileConfig {
    
    @Bean
    public MemberRepository memberRepository() {
        return new FakeMemberRepository();
    }
}
```

**테스트 태그 정의**
```java
// src/test/java/com/example/member/tag/TestTags.java
package com.example.member.tag;

import org.junit.jupiter.api.Tag;

public class TestTags {
    public static final String UNIT_TEST = "unit";
    public static final String INTEGRATION_TEST = "integration";
    public static final String SLOW_TEST = "slow";
    public static final String FAST_TEST = "fast";
    public static final String SECURITY_TEST = "security";
    public static final String PERFORMANCE_TEST = "performance";
}
```

### 2. 계층별 테스트 구현

**도메인 계층 테스트**
```java
// src/test/java/com/example/member/domain/MemberDomainTest.java
package com.example.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.example.member.tag.TestTags.UNIT_TEST;
import static org.junit.jupiter.api.Assertions.*;

@Tag(UNIT_TEST)
@Tag("fast")
class MemberDomainTest {

    @Test
    @DisplayName("회원 생성 - 정상 케이스")
    void createMember_success() {
        // Given & When
        Member member = Member.createMember("test@example.com", "홍길동", "password123");

        // Then
        assertNotNull(member);
        assertEquals("test@example.com", member.getEmail());
        assertEquals("홍길동", member.getName());
        assertEquals("password123", member.getPassword());
    }

    @Test
    @DisplayName("회원 생성 - 이메일 형식 검증")
    void createMember_invalidEmail_throwsException() {
        // Given & When & Then
        assertThrows(IllegalArgumentException.class, () -> 
            Member.createMember("invalid-email", "홍길동", "password123")
        );
    }

    @Test
    @DisplayName("회원 이름 수정 - 정상 케이스")
    void updateName_success() {
        // Given
        Member member = Member.createMember("test@example.com", "기존이름", "password");

        // When
        member.updateName("새로운이름");

        // Then
        assertEquals("새로운이름", member.getName());
    }
}
```

**서비스 계층 테스트**
```java
// src/test/java/com/example/member/service/MemberServiceLayerTest.java
package com.example.member.service;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.member.tag.TestTags.UNIT_TEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag(UNIT_TEST)
@Tag("fast")
@ExtendWith(MockitoExtension.class)
class MemberServiceLayerTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = Member.createMember("test@example.com", "테스트유저", "password123");
    }

    @Test
    @DisplayName("비즈니스 로직 - 회원 등록 성공")
    void register_businessLogic_success() {
        // Given
        when(memberRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(testMember);

        // When
        Long memberId = memberService.register("test@example.com", "테스트유저", "password123");

        // Then
        assertNotNull(memberId);
        verify(memberRepository, times(1)).existsByEmail("test@example.com");
        verify(memberRepository, times(1)).save(any(Member.class));
    }
}
```

**통합 테스트**
```java
// src/test/java/com/example/member/integration/MemberIntegrationTest.java
package com.example.member.integration;

import com.example.member.controller.MemberController;
import com.example.member.dto.MemberRequest;
import com.example.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.member.tag.TestTags.INTEGRATION_TEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag(INTEGRATION_TEST)
@Tag("slow")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MemberIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("전체 API 흐름 통합 테스트")
    void fullApiFlow_integrationTest() throws Exception {
        // 1. 회원 등록
        MemberRequest registerRequest = new MemberRequest("integration@test.com", "통합테스트", "password123");
        
        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.message").value("회원 가입 성공"));

        // 2. 회원 조회 (등록된 회원 ID를 알아야 하므로 서비스 통해 조회)
        Long memberId = 1L; // 간단화를 위해 고정 ID 사용

        mockMvc.perform(get("/api/members/{id}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("integration@test.com"))
                .andExpect(jsonPath("$.name").value("통합테스트"));
    }

    @Test
    @DisplayName("API 유효성 검증 통합 테스트")
    void apiValidation_integrationTest() throws Exception {
        // 잘못된 이메일 형식
        MemberRequest invalidRequest = new MemberRequest("invalid-email", "홍", "123");

        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
```

### 3. CI/CD 통합을 위한 테스트 설정

**Maven Surefire & Failsafe 설정 (pom.xml)**
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M9</version>
            <configuration>
                <includes>
                    <include>**/*Test.java</include>
                </includes>
                <excludes>
                    <exclude>**/*IntegrationTest.java</exclude>
                    <exclude>**/*PerformanceTest.java</exclude>
                </excludes>
                <groups>unit,fast</groups>
            </configuration>
        </plugin>
        
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-failsafe-plugin</artifactId>
            <version>3.0.0-M9</version>
            <configuration>
                <includes>
                    <include>**/*IntegrationTest.java</include>
                </includes>
                <groups>integration</groups>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>integration-test</goal>
                        <goal>verify</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.8</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
                <execution>
                    <id>check</id>
                    <phase>verify</phase>
                    <goals>
                        <goal>check</goal>
                    </goals>
                    <configuration>
                        <rules>
                            <rule>
                                <element>BUNDLE</element>
                                <limits>
                                    <limit>
                                        <counter>LINE</counter>
                                        <value>COVEREDRATIO</value>
                                        <minimum>0.80</minimum>
                                    </limit>
                                    <limit>
                                        <counter>BRANCH</counter>
                                        <value>COVEREDRATIO</value>
                                        <minimum>0.70</minimum>
                                    </limit>
                                </limits>
                            </rule>
                        </rules>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### 4. GitHub Actions를 이용한 CI 설정

```yaml
# .github/workflows/ci-cd.yml
name: Java CI with Maven

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: maven
        
    - name: Run unit tests
      run: mvn surefire:test -Dgroups=unit,fast
      
    - name: Run integration tests
      run: mvn failsafe:integration-test -Dgroups=integration
      
    - name: Verify coverage
      run: mvn jacoco:check
      
    - name: Generate coverage report
      run: mvn jacoco:report
      
    - name: Upload coverage to Codecov
      uses: codecov/codecov-action@v3
      with:
        file: ./target/site/jacoco/jacoco.xml
        flags: unittests
        name: codecov-umbrella
        
    - name: Build application
      run: mvn package -DskipTests
```

### 5. 테스트 데이터 관리 전략

**테스트 데이터 팩토리**
```java
// src/test/java/com/example/member/factory/TestDataFactory.java
package com.example.member.factory;

import com.example.member.domain.Member;
import com.example.member.dto.MemberRequest;

import java.util.ArrayList;
import java.util.List;

public class TestDataFactory {
    
    public static Member createMember() {
        return Member.createMember("default@test.com", "기본사용자", "password123");
    }
    
    public static Member createMember(String email, String name) {
        return Member.createMember(email, name, "password123");
    }
    
    public static MemberRequest createMemberRequest() {
        return new MemberRequest("default@test.com", "기본사용자", "password123");
    }
    
    public static List<Member> createMembers(int count) {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            members.add(createMember("user" + i + "@test.com", "User" + i));
        }
        return members;
    }
    
    public static Member createInvalidMember() {
        return Member.createMember("invalid", "", "");
    }
}
```

**데이터베이스 테스트 유틸리티**
```java
// src/test/java/com/example/member/util/DatabaseTestUtil.java
package com.example.member.util;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseTestUtil {
    
    @Autowired
    private MemberRepository memberRepository;
    
    public void clearDatabase() {
        memberRepository.deleteAll();
    }
    
    public Member saveTestMember(String email, String name) {
        Member member = Member.createMember(email, name, "password");
        return memberRepository.save(member);
    }
    
    public List<Member> saveTestMembers(int count) {
        List<Member> members = TestDataFactory.createMembers(count);
        return memberRepository.saveAll(members);
    }
    
    public Long getMemberCount() {
        return memberRepository.count();
    }
}
```

### 6. E2E 테스트 구현

```java
// src/test/java/com/example/member/e2e/MemberE2ETest.java
package com.example.member.e2e;

import com.example.member.dto.MemberRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static com.example.member.tag.TestTags.INTEGRATION_TEST;
import static org.junit.jupiter.api.Assertions.*;

@Tag(INTEGRATION_TEST)
@Tag("slow")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class MemberE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/members";
    }

    @Test
    @DisplayName("E2E - 회원 전체 생명주기 테스트")
    void memberFullLifecycle_e2e() {
        // 1. 회원 등록
        MemberRequest registerRequest = new MemberRequest("e2e@test.com", "E2E테스트", "password123");
        
        ResponseEntity<String> registerResponse = restTemplate.postForEntity(
            baseUrl, registerRequest, String.class);
        
        assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
        assertNotNull(registerResponse.getBody());

        // 2. 회원 조회 (간소화를 위해 ID를 하드코딩)
        Long memberId = 1L;
        
        ResponseEntity<String> getResponse = restTemplate.getForEntity(
            baseUrl + "/" + memberId, String.class);
        
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertTrue(getResponse.getBody().contains("e2e@test.com"));
    }

    @Test
    @DisplayName("E2E - 중복 이메일 회원가입 실패")
    void duplicateRegistration_e2e() {
        // 첫 번째 회원 등록
        MemberRequest request = new MemberRequest("duplicate@test.com", "첫번째", "password");
        restTemplate.postForEntity(baseUrl, request, String.class);

        // 두 번째 회원 등록 (동일 이메일)
        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl, request, String.class);

        // 실패 응답 확인
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
```

### 7. 테스트 리포트와 모니터링

**테스트 리포트 생성 설정**
```java
// src/test/java/com/example/member/config/TestExecutionListener.java
package com.example.member.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestContext;

public class CustomTestExecutionListener implements TestExecutionListener {
    
    @Override
    public void beforeTestClass(TestContext testContext) {
        System.out.println("=== Starting Test Class: " + testContext.getTestClass().getSimpleName() + " ===");
    }
    
    @Override
    public void afterTestClass(TestContext testContext) {
        System.out.println("=== Finished Test Class: " + testContext.getTestClass().getSimpleName() + " ===");
    }
}
```

### 8. 실제 프로젝트 적용 체크리스트

```java
// src/test/java/com/example/member/checklist/TestChecklist.java
package com.example.member.checklist;

/**
 * 테스트 체크리스트 - 실제 프로젝트 적용 시 확인할 사항들
 */
public class TestChecklist {
    
    /**
     * 단위 테스트 체크리스트
     */
    public static class UnitTestChecklist {
        // ✅ 테스트는 격리되어 있는가?
        // ✅ 테스트는 빠르게 실행되는가? (1초 이내)
        // ✅ Given-When-Then 패턴을 따르는가?
        // ✅ 의미 있는 테스트 이름을 사용하는가?
        // ✅ 하나의 테스트는 하나의 동작만 검증하는가?
        // ✅ 예외 케이스를 충분히 테스트하는가?
    }
    
    /**
     * 통합 테스트 체크리스트
     */
    public static class IntegrationTestChecklist {
        // ✅ 실제 데이터베이스를 사용하는가?
        // ✅ 트랜잭션 관리를 올바르게 하는가?
        // ✅ 테스트 후 데이터를 정리하는가?
        // ✅ 외부 의존성을 Mocking하는가?
    }
    
    /**
     * E2E 테스트 체크리스트
     */
    public static class E2ETestChecklist {
        // ✅ 전체 시스템 흐름을 테스트하는가?
        // ✅ 실제 환경과 유사한 설정을 사용하는가?
        // ✅ 사용자 시나리오를 반영하는가?
    }
    
    /**
     * CI/CD 통합 체크리스트
     */
    public static class CICDChecklist {
        // ✅ CI 파이프라인에 테스트가 통합되었는가?
        // ✅ 테스트 실패 시 빌드가 실패하는가?
        // ✅ 코드 커버리지 기준을 만족하는가?
        // ✅ 성능 테스트가 포함되어 있는가?
    }
}
```

## 최종 실습 문제

1. **테스트 전략 수립**
   - 프로젝트에 맞는 테스트 전략 문서 작성
   - 테스트 범위, 목표, 품질 기준 정의

2. **CI/CD 파이프라인 구축**
   - GitHub Actions를 이용한 자동화된 테스트 파이프라인 구현
   - 테스트 결과 리포트 자동 생성

3. **테스트 커버리지 개선**
   - JaCoCo 리포트 분석 및 미달성 부분 테스트 작성
   - 커버리지 80% 이상 달성

4. **성능 테스트 강화**
   - 부하 테스트 시나리오 작성
   - 성능 기준 설정 및 모니터링

## 마무리

**5단계 학습을 통해 배운 내용:**
1. ✅ Junit 기본 사용법과 Spring Boot 통합
2. ✅ 다양한 테스트 어노테이션과 Mockito 활용
3. ✅ 테스트 전략과 Given-When-Then 패턴
4. ✅ 테스트 리팩토링과 테스트 더블
5. ✅ 테스트 전략 수립과 CI/CD 통합

**앞으로의 학습 방향:**
- 테스트 주도 개발(TDD) 실천
- 행위 주도 개발(BDD) 도구 학습 (Cucumber 등)
- 성능 테스트 도구 활용 (JMeter, Gatling)
- 테스트 자동화와 데브옵스 통합

이제 실제 프로젝트에 체계적인 테스트를 적용할 수 있는 준비가 되었습니다! 🎉
