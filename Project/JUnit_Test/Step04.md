# 4단계: 테스트 리팩토링, 테스트 더블, 성능 테스트

## 이론 부분

### 테스트 리팩토링 (Test Refactoring)

**테스트 코드의 유지보수성 향상을 위한 패턴**
- 테스트 픽스처 관리 (Fixture Management)
- 테스트 데이터 빌더 패턴 (Test Data Builder)
- 커스텀 Assertion 생성
- 중복 코드 제거

### 테스트 더블 (Test Double) 종류
- **Dummy**: 실제 사용되지 않는 객체, 단순히 파라미터 채우기용
- **Fake**: 실제 동작을 단순하게 구현한 객체 (메모리 DB 등)
- **Stub**: 미리 준비된 결과를 반환하는 객체
- **Mock**: 행위 검증을 위한 객체, 호출 여부와 횟수 검증
- **Spy**: 실제 객체를 wrapping하여 특정 메소드 호출 감시

### 성능 테스트
- 실행 시간 측정 (`@Timeout`)
- 부하 테스트 (동시성 테스트)
- 메모리 사용량 측정

## 실습 부분

### 1. 테스트 리팩토링 - 테스트 데이터 빌더 패턴

```java
// src/test/java/com/example/member/builder/MemberTestBuilder.java
package com.example.member.builder;

import com.example.member.domain.Member;
import com.example.member.dto.MemberRequest;

public class MemberTestBuilder {
    
    private Long id;
    private String email = "default@example.com";
    private String name = "기본이름";
    private String password = "defaultPassword";
    
    private MemberTestBuilder() {}
    
    public static MemberTestBuilder builder() {
        return new MemberTestBuilder();
    }
    
    public MemberTestBuilder email(String email) {
        this.email = email;
        return this;
    }
    
    public MemberTestBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public MemberTestBuilder password(String password) {
        this.password = password;
        return this;
    }
    
    public MemberTestBuilder id(Long id) {
        this.id = id;
        return this;
    }
    
    public Member build() {
        Member member = Member.createMember(email, name, password);
        // 리플렉션을 사용하여 id 설정 (실제 프로젝트에서는 주의해서 사용)
        try {
            var idField = Member.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(member, id);
        } catch (Exception e) {
            throw new RuntimeException("ID 설정 실패", e);
        }
        return member;
    }
    
    public MemberRequest buildRequest() {
        return new MemberRequest(email, name, password);
    }
}
```

### 2. 커스텀 Assertion 클래스 생성

```java
// src/test/java/com/example/member/assertion/MemberAssertions.java
package com.example.member.assertion;

import com.example.member.domain.Member;
import com.example.member.dto.MemberResponse;
import org.assertj.core.api.AbstractAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberAssertions extends AbstractAssert<MemberAssertions, Member> {

    public MemberAssertions(Member actual) {
        super(actual, MemberAssertions.class);
    }
    
    public static MemberAssertions assertThatMember(Member actual) {
        return new MemberAssertions(actual);
    }
    
    public MemberAssertions hasEmail(String expectedEmail) {
        isNotNull();
        assertThat(actual.getEmail())
            .withFailMessage("Expected email to be <%s> but was <%s>", expectedEmail, actual.getEmail())
            .isEqualTo(expectedEmail);
        return this;
    }
    
    public MemberAssertions hasName(String expectedName) {
        isNotNull();
        assertThat(actual.getName())
            .withFailMessage("Expected name to be <%s> but was <%s>", expectedName, actual.getName())
            .isEqualTo(expectedName);
        return this;
    }
    
    public MemberAssertions isValid() {
        isNotNull();
        assertThat(actual.getEmail()).contains("@");
        assertThat(actual.getName()).isNotBlank();
        assertThat(actual.getPassword()).isNotBlank();
        return this;
    }
}

// Response용 Assertion
class MemberResponseAssertions extends AbstractAssert<MemberResponseAssertions, MemberResponse> {
    
    public MemberResponseAssertions(MemberResponse actual) {
        super(actual, MemberResponseAssertions.class);
    }
    
    public static MemberResponseAssertions assertThatResponse(MemberResponse actual) {
        return new MemberResponseAssertions(actual);
    }
    
    public MemberResponseAssertions hasSuccessfulMessage() {
        isNotNull();
        assertThat(actual.getMessage())
            .withFailMessage("Expected message to contain '성공' but was <%s>", actual.getMessage())
            .contains("성공");
        return this;
    }
    
    public MemberResponseAssertions hasId(Long expectedId) {
        isNotNull();
        assertThat(actual.getId())
            .withFailMessage("Expected ID to be <%s> but was <%s>", expectedId, actual.getId())
            .isEqualTo(expectedId);
        return this;
    }
}
```

### 3. 리팩토링된 서비스 테스트

```java
// src/test/java/com/example/member/service/RefactoredMemberServiceTest.java
package com.example.member.service;

import com.example.member.builder.MemberTestBuilder;
import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.member.assertion.MemberAssertions.assertThatMember;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefactoredMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member testMember;

    @BeforeEach
    void setUp() {
        // 테스트 빌더를 사용한 객체 생성
        testMember = MemberTestBuilder.builder()
            .id(1L)
            .email("test@example.com")
            .name("테스트유저")
            .password("password123")
            .build();
    }

    @Test
    @DisplayName("빌더 패턴을 사용한 회원 등록 테스트")
    void register_withBuilderPattern() {
        // Given
        String email = "new@example.com";
        String name = "새로운회원";
        String password = "newPassword";
        
        when(memberRepository.existsByEmail(email)).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            return MemberTestBuilder.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .id(1L)
                .build();
        });

        // When
        Long memberId = memberService.register(email, name, password);

        // Then
        assertNotNull(memberId);
        verify(memberRepository).existsByEmail(email);
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    @DisplayName("커스텀 Assertion을 사용한 회원 검증")
    void findMember_withCustomAssertions() {
        // Given
        when(memberRepository.findById(1L)).thenReturn(Optional.of(testMember));

        // When
        Member foundMember = memberService.findMember(1L);

        // Then - 커스텀 Assertion 사용
        assertThatMember(foundMember)
            .hasEmail("test@example.com")
            .hasName("테스트유저")
            .isValid();
    }

    @Test
    @DisplayName("여러 회원 조회 시 빌더 패턴 활용")
    void findAllMembers_withBuilderPattern() {
        // Given
        List<Member> expectedMembers = Arrays.asList(
            MemberTestBuilder.builder().id(1L).email("user1@test.com").name("유저1").build(),
            MemberTestBuilder.builder().id(2L).email("user2@test.com").name("유저2").build(),
            MemberTestBuilder.builder().id(3L).email("user3@test.com").name("유저3").build()
        );
        
        when(memberRepository.findAll()).thenReturn(expectedMembers);

        // When
        List<Member> result = memberService.findAllMembers();

        // Then
        assertEquals(3, result.size());
        
        // 각 회원 검증
        assertThatMember(result.get(0))
            .hasEmail("user1@test.com")
            .hasName("유저1");
            
        assertThatMember(result.get(1))
            .hasEmail("user2@test.com")
            .hasName("유저2");
    }
}
```

### 4. 다양한 테스트 더블 구현

```java
// src/test/java/com/example/member/doubles/FakeMemberRepository.java
package com.example.member.doubles;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class FakeMemberRepository implements MemberRepository {
    
    private final Map<Long, Member> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1L);

    @Override
    public Optional<Member> findByEmail(String email) {
        return storage.values().stream()
            .filter(member -> member.getEmail().equals(email))
            .findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        return storage.values().stream()
            .anyMatch(member -> member.getEmail().equals(email));
    }

    @Override
    public List<Member> findByNameContaining(String name) {
        return storage.values().stream()
            .filter(member -> member.getName().contains(name))
            .toList();
    }

    // JpaRepository 기본 메소드 구현
    @Override
    public Member save(Member member) {
        if (member.getId() == null) {
            Long newId = idGenerator.getAndIncrement();
            // 리플렉션으로 ID 설정
            try {
                var idField = Member.class.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(member, newId);
            } catch (Exception e) {
                throw new RuntimeException("ID 설정 실패", e);
            }
        }
        storage.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public long count() {
        return storage.size();
    }

    // 나머지 메소드들은 기본 구현 제공
    @Override public List<Member> findAll(Sort sort) { return findAll(); }
    @Override public Page<Member> findAll(Pageable pageable) { return Page.empty(); }
    @Override public List<Member> findAllById(Iterable<Long> ids) { return List.of(); }
    @Override public <S extends Member> List<S> saveAll(Iterable<S> entities) { return List.of(); }
    @Override public void flush() {}
    @Override public <S extends Member> S saveAndFlush(S entity) { return entity; }
    @Override public <S extends Member> List<S> saveAllAndFlush(Iterable<S> entities) { return List.of(); }
    @Override public void deleteAllInBatch(Iterable<Member> entities) {}
    @Override public void deleteAllByIdInBatch(Iterable<Long> ids) {}
    @Override public void deleteAllInBatch() {}
    @Override public Member getOne(Long id) { return null; }
    @Override public Member getById(Long id) { return null; }
    @Override public Member getReferenceById(Long id) { return findById(id).orElseThrow(); }
    @Override public <S extends Member> Optional<S> findOne(Example<S> example) { return Optional.empty(); }
    @Override public <S extends Member> List<S> findAll(Example<S> example) { return List.of(); }
    @Override public <S extends Member> List<S> findAll(Example<S> example, Sort sort) { return List.of(); }
    @Override public <S extends Member> Page<S> findAll(Example<S> example, Pageable pageable) { return Page.empty(); }
    @Override public <S extends Member> long count(Example<S> example) { return 0; }
    @Override public <S extends Member> boolean exists(Example<S> example) { return false; }
    @Override public <S extends Member, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) { return null; }
    @Override public boolean existsById(Long id) { return storage.containsKey(id); }
    @Override public void delete(Member entity) { storage.remove(entity.getId()); }
    @Override public void deleteAllById(Iterable<? extends Long> ids) { ids.forEach(storage::remove); }
    @Override public void deleteAll(Iterable<? extends Member> entities) { entities.forEach(e -> storage.remove(e.getId())); }
    @Override public void deleteAll() { storage.clear(); }
}
```

### 5. Fake Repository를 사용한 테스트

```java
// src/test/java/com/example/member/service/FakeRepositoryTest.java
package com.example.member.service;

import com.example.member.doubles.FakeMemberRepository;
import com.example.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.member.assertion.MemberAssertions.assertThatMember;
import static org.junit.jupiter.api.Assertions.*;

class FakeRepositoryTest {

    private FakeMemberRepository fakeMemberRepository;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        fakeMemberRepository = new FakeMemberRepository();
        memberService = new MemberService(fakeMemberRepository);
    }

    @Test
    @DisplayName("Fake Repository를 사용한 회원 등록 및 조회")
    void registerAndFind_withFakeRepository() {
        // Given
        String email = "fake@test.com";
        String name = "FakeUser";
        String password = "password";

        // When
        Long memberId = memberService.register(email, name, password);
        Member foundMember = memberService.findMember(memberId);

        // Then
        assertThatMember(foundMember)
            .hasEmail(email)
            .hasName(name)
            .isValid();
    }

    @Test
    @DisplayName("Fake Repository를 사용한 중복 이메일 검증")
    void duplicateEmail_withFakeRepository() {
        // Given
        memberService.register("duplicate@test.com", "첫번째", "pass1");

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> memberService.register("duplicate@test.com", "두번째", "pass2"));
        
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("Fake Repository를 사용한 여러 회원 조회")
    void multipleMembers_withFakeRepository() {
        // Given
        memberService.register("user1@test.com", "김유저", "pass1");
        memberService.register("user2@test.com", "이사용자", "pass2");
        memberService.register("user3@test.com", "삼테스트", "pass3");

        // When
        List<Member> allMembers = memberService.findAllMembers();
        List<Member> searchResults = memberService.findMembersByName("유저");

        // Then
        assertEquals(3, allMembers.size());
        assertEquals(1, searchResults.size());
        assertEquals("김유저", searchResults.get(0).getName());
    }
}
```

### 6. 성능 테스트 구현

```java
// src/test/java/com/example/member/performance/MemberServicePerformanceTest.java
package com.example.member.performance;

import com.example.member.doubles.FakeMemberRepository;
import com.example.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MemberServicePerformanceTest {

    private MemberService memberService;
    private FakeMemberRepository fakeMemberRepository;

    @BeforeEach
    void setUp() {
        fakeMemberRepository = new FakeMemberRepository();
        memberService = new MemberService(fakeMemberRepository);
    }

    @Test
    @Timeout(5) // 5초 안에 테스트 완료되어야 함
    @DisplayName("단일 회원 등록 성능 테스트")
    void singleRegistration_performance() {
        // Given
        long startTime = System.currentTimeMillis();

        // When
        for (int i = 0; i < 1000; i++) {
            memberService.register("user" + i + "@test.com", "User" + i, "password");
        }

        // Then
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("1000회 회원 등록 소요 시간: " + duration + "ms");
        assertTrue(duration < 3000, "1000회 등록이 3초 내에 완료되어야 함");
    }

    @Test
    @DisplayName("동시성 회원 등록 테스트")
    void concurrentRegistration_test() throws InterruptedException {
        // Given
        int threadCount = 10;
        int registrationPerThread = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failureCount = new AtomicInteger();

        // When
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < registrationPerThread; j++) {
                        String email = String.format("user%d_%d@test.com", threadId, j);
                        try {
                            memberService.register(email, "User" + threadId, "password");
                            successCount.incrementAndGet();
                        } catch (Exception e) {
                            failureCount.incrementAndGet();
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        // Then
        assertTrue(latch.await(10, TimeUnit.SECONDS), "모든 스레드가 10초 내에 완료되어야 함");
        executorService.shutdown();
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("동시성 테스트 결과:");
        System.out.println("성공: " + successCount.get());
        System.out.println("실패: " + failureCount.get());
        System.out.println("소요 시간: " + duration + "ms");
        
        assertEquals(threadCount * registrationPerThread, successCount.get() + failureCount.get());
        assertTrue(duration < 5000, "동시성 테스트가 5초 내에 완료되어야 함");
    }
}
```

### 7. Spy를 사용한 테스트

```java
// src/test/java/com/example/member/service/MemberServiceSpyTest.java
package com.example.member.service;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceSpyTest {

    @Spy
    private MemberRepository memberRepository; // 실제 객체를 Spy로 감싸기

    @Test
    void spyTest_withRealMethodCalls() {
        // Spy는 실제 메소드를 호출하지만, 특정 메소드만 Stub할 수 있음
        MemberService memberService = new MemberService(memberRepository);
        
        // 실제 save 메소드 호출
        Member member = Member.createMember("spy@test.com", "SpyUser", "password");
        
        // when().thenReturn()으로 특정 메소드만 Stub
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        
        // 테스트 실행
        memberService.register("spy@test.com", "SpyUser", "password");
        
        // verify로 호출 검증
        verify(memberRepository, times(1)).existsByEmail("spy@test.com");
        verify(memberRepository, times(1)).save(any(Member.class));
    }
}
```

## 실습 문제

1. **테스트 리팩토링 적용**
   - 기존 테스트 코드를 빌더 패턴과 커스텀 Assertion으로 리팩토링
   - 중복 코드를 제거하고 가독성 향상

2. **다양한 테스트 더블 구현**
   - Stub을 사용한 외부 API 호출 테스트 구현
   - Spy를 사용한 특정 메소드 호출 횟수 검증 테스트

3. **성능 테스트 확장**
   - 메모리 사용량 측정 테스트 추가
   - 대량 데이터 처리 성능 테스트 구현

**다음 5단계에서는 테스트 전략 수립, CI/CD 통합, 실제 프로젝트 적용 사례에 대해 배워보겠습니다!**
