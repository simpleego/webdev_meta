# 3단계: 테스트 전략, Given-When-Then 패턴, 테스트 커버리지

## 이론 부분

### 테스트 전략 (Test Strategy)

**테스트 피라미드**
```
      /\
     /UI\        - E2E 테스트 (소량)
    /----\
   /Service/     - 통합 테스트 (중간)
  /--------\
 /   Unit   \    - 단위 테스트 (다량)
/------------\
```

**테스트 종류별 특징**
- **단위 테스트**: 개별 컴포넌트 검증, 빠름, 격리됨
- **통합 테스트**: 컴포넌트 간 상호작용 검증
- **E2E 테스트**: 전체 시스템 흐름 검증, 느림

### Given-When-Then 패턴
- **Given**: 테스트를 위한 사전 조건 설정
- **When**: 실제 테스트 대상 행동 실행
- **Then**: 예상 결과 검증

### 테스트 커버리지
- **라인 커버리지**: 실행된 코드 라인 비율
- **브랜치 커버리지**: 조건문의 참/거짓 경로 커버리지
- **메소드 커버리지**: 메소드 호출 커버리지

## 실습 부분

### 1. Given-When-Then 패턴을 적용한 서비스 테스트

```java
// src/test/java/com/example/member/service/MemberServiceGBTPatternTest.java
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
class MemberServiceGBTPatternTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원 등록 - 정상 흐름")
    void register_success() {
        // Given - 테스트 준비
        String email = "test@example.com";
        String name = "홍길동";
        String password = "password123";
        
        when(memberRepository.existsByEmail(email)).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            return Member.createMember(member.getEmail(), member.getName(), member.getPassword());
        });

        // When - 테스트 실행
        Long memberId = memberService.register(email, name, password);

        // Then - 결과 검증
        assertNotNull(memberId);
        verify(memberRepository).existsByEmail(email);
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 등록 - 중복 이메일 예외")
    void register_duplicateEmail_throwsException() {
        // Given
        String duplicateEmail = "duplicate@example.com";
        String name = "홍길동";
        String password = "password123";
        
        when(memberRepository.existsByEmail(duplicateEmail)).thenReturn(true);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> memberService.register(duplicateEmail, name, password));
        
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @DisplayName("회원 조회 - 존재하는 회원")
    void findMember_existingMember() {
        // Given
        Long memberId = 1L;
        Member expectedMember = Member.createMember("test@example.com", "홍길동", "password");
        
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(expectedMember));

        // When
        Member foundMember = memberService.findMember(memberId);

        // Then
        assertNotNull(foundMember);
        assertEquals(expectedMember.getEmail(), foundMember.getEmail());
        assertEquals(expectedMember.getName(), foundMember.getName());
    }

    @Test
    @DisplayName("회원 조회 - 존재하지 않는 회원 예외")
    void findMember_nonExistingMember_throwsException() {
        // Given
        Long nonExistingId = 999L;
        
        when(memberRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> memberService.findMember(nonExistingId));
        
        assertEquals("회원을 찾을 수 없습니다.", exception.getMessage());
    }
}
```

### 2. 다양한 테스트 시나리오를 위한 커스텀 메소드 추가

**서비스에 새로운 메소드 추가**
```java
// src/main/java/com/example/member/service/MemberService.java (추가 메소드)
public List<Member> findAllMembers() {
    return memberRepository.findAll();
}

public List<Member> findMembersByName(String name) {
    return memberRepository.findByNameContaining(name);
}

public void changePassword(Long id, String oldPassword, String newPassword) {
    Member member = findMember(id);
    // 간단한 비밀번호 검증 (실제로는 암호화 비교)
    if (!member.getPassword().equals(oldPassword)) {
        throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
    }
    // 비밀번호 업데이트 로직 (실제로는 암호화)
    // member.updatePassword(newPassword);
}
```

**Repository 커스텀 메소드 추가**
```java
// src/main/java/com/example/member/repository/MemberRepository.java (추가)
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Member> findByNameContaining(String name); // 추가
}
```

### 3. 다양한 테스트 시나리오 구현

```java
// src/test/java/com/example/member/service/MemberServiceScenarioTest.java
package com.example.member.service;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceScenarioTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        member1 = Member.createMember("user1@example.com", "김유저", "pass123");
        member2 = Member.createMember("user2@example.com", "이사용자", "pass456");
    }

    @Nested
    @DisplayName("회원 목록 조회 시나리오")
    class FindAllMembersScenarios {

        @Test
        @DisplayName("회원이 존재할 때 전체 목록 조회")
        void whenMembersExist_thenReturnAllMembers() {
            // Given
            List<Member> expectedMembers = Arrays.asList(member1, member2);
            when(memberRepository.findAll()).thenReturn(expectedMembers);

            // When
            List<Member> result = memberService.findAllMembers();

            // Then
            assertEquals(2, result.size());
            assertTrue(result.contains(member1));
            assertTrue(result.contains(member2));
            verify(memberRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("회원이 없을 때 빈 목록 반환")
        void whenNoMembers_thenReturnEmptyList() {
            // Given
            when(memberRepository.findAll()).thenReturn(Arrays.asList());

            // When
            List<Member> result = memberService.findAllMembers();

            // Then
            assertTrue(result.isEmpty());
            verify(memberRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("이름으로 회원 검색 시나리오")
    class FindMembersByNameScenarios {

        @Test
        @DisplayName("이름에 포함된 문자열로 검색")
        void whenSearchByName_thenReturnMatchingMembers() {
            // Given
            String searchName = "유저";
            List<Member> expectedMembers = Arrays.asList(member1);
            when(memberRepository.findByNameContaining(searchName)).thenReturn(expectedMembers);

            // When
            List<Member> result = memberService.findMembersByName(searchName);

            // Then
            assertEquals(1, result.size());
            assertEquals(member1, result.get(0));
            verify(memberRepository, times(1)).findByNameContaining(searchName);
        }

        @Test
        @DisplayName("검색 결과가 없을 때 빈 목록 반환")
        void whenNoMatchingName_thenReturnEmptyList() {
            // Given
            String searchName = "존재하지않는이름";
            when(memberRepository.findByNameContaining(searchName)).thenReturn(Arrays.asList());

            // When
            List<Member> result = memberService.findMembersByName(searchName);

            // Then
            assertTrue(result.isEmpty());
            verify(memberRepository, times(1)).findByNameContaining(searchName);
        }
    }

    @Nested
    @DisplayName("비밀번호 변경 시나리오")
    class ChangePasswordScenarios {

        @Test
        @DisplayName("정상적인 비밀번호 변경")
        void whenValidOldPassword_thenChangePassword() {
            // Given
            Long memberId = 1L;
            String oldPassword = "currentPass";
            String newPassword = "newPass";
            
            Member member = Member.createMember("test@example.com", "홍길동", oldPassword);
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

            // When & Then
            assertDoesNotThrow(() -> 
                memberService.changePassword(memberId, oldPassword, newPassword)
            );
        }

        @Test
        @DisplayName("잘못된 기존 비밀번호로 변경 시도")
        void whenInvalidOldPassword_thenThrowException() {
            // Given
            Long memberId = 1L;
            String wrongOldPassword = "wrongPass";
            String newPassword = "newPass";
            
            Member member = Member.createMember("test@example.com", "홍길동", "correctPass");
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.changePassword(memberId, wrongOldPassword, newPassword));
            
            assertEquals("기존 비밀번호가 일치하지 않습니다.", exception.getMessage());
        }
    }
}
```

### 4. 테스트 커버리지 측정을 위한 설정

**pom.xml에 JaCoCo 플러그인 추가**
```xml
<build>
    <plugins>
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
            </executions>
        </plugin>
    </plugins>
</build>
```

**테스트 커버리지 리포트 생성**
```bash
mvn clean test jacoco:report
```

### 5. 통합 테스트 전략 구현

```java
// src/test/java/com/example/member/integration/MemberServiceIntegrationTest.java
package com.example.member.integration;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import com.example.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class MemberServiceIntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 전체 생명주기 통합 테스트")
    void memberFullLifecycleIntegrationTest() {
        // Given
        String email = "lifecycle@test.com";
        String name = "생명주기테스트";
        String password = "password123";

        // When 1 - 회원 등록
        Long memberId = memberService.register(email, name, password);

        // Then 1 - 등록 확인
        assertNotNull(memberId);
        Member savedMember = memberService.findMember(memberId);
        assertEquals(email, savedMember.getEmail());
        assertEquals(name, savedMember.getName());

        // When 2 - 전체 회원 조회
        List<Member> allMembers = memberService.findAllMembers();

        // Then 2 - 조회 결과 확인
        assertEquals(1, allMembers.size());
        assertEquals(email, allMembers.get(0).getEmail());

        // When 3 - 이름으로 검색
        List<Member> searchResults = memberService.findMembersByName("생명주기");

        // Then 3 - 검색 결과 확인
        assertEquals(1, searchResults.size());
        assertEquals(name, searchResults.get(0).getName());
    }

    @Test
    @DisplayName("다중 회원 등록 및 조회 통합 테스트")
    void multipleMembersIntegrationTest() {
        // Given
        memberService.register("user1@test.com", "김첫번째", "pass1");
        memberService.register("user2@test.com", "이두번째", "pass2");
        memberService.register("user3@test.com", "삼세번째", "pass3");

        // When
        List<Member> allMembers = memberService.findAllMembers();
        List<Member> searchResults = memberService.findMembersByName("번째");

        // Then
        assertEquals(3, allMembers.size());
        assertEquals(3, searchResults.size());
        
        // 이름 검색이 정상적으로 작동하는지 확인
        assertTrue(searchResults.stream()
            .allMatch(member -> member.getName().contains("번째")));
    }
}
```

### 6. 테스트 유틸리티 클래스 생성

```java
// src/test/java/com/example/member/util/TestMemberFactory.java
package com.example.member.util;

import com.example.member.domain.Member;
import com.example.member.dto.MemberRequest;

public class TestMemberFactory {
    
    private TestMemberFactory() {
        // 유틸리티 클래스
    }
    
    public static Member createMember() {
        return Member.createMember("test@example.com", "테스트유저", "password123");
    }
    
    public static Member createMember(String email, String name) {
        return Member.createMember(email, name, "password123");
    }
    
    public static MemberRequest createMemberRequest() {
        return new MemberRequest("test@example.com", "테스트유저", "password123");
    }
    
    public static MemberRequest createMemberRequest(String email, String name) {
        return new MemberRequest(email, name, "password123");
    }
}
```

## 실습 문제

1. **테스트 커버리지 목표 설정**
   - JaCoCo를 사용하여 80% 이상의 라인 커버리지 달성
   - 커버리지
