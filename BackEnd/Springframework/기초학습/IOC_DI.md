# 스프링 기초 학습
> IoC와 DI의 핵심 개념

### IoC (Inversion of Control)와 DI (Dependency Injection) 핵심 개념

코드를 보기 전에 두 개념을 간단히 이해해 보겠습니다.

  * **IoC (제어의 역전, Inversion of Control)**: 기존에는 개발자가 코드 안에서 사용할 객체를 직접 생성하고(new MemberRepositoryImpl()), 의존관계를 설정했습니다. IoC는 이 **제어권**을 개발자가 아닌 **Spring 컨테이너**에 넘기는 것을 의미합니다. Spring이 직접 객체의 생명주기(생성, 관리, 소멸)를 관리하게 됩니다.

  * **DI (의존성 주입, Dependency Injection)**: IoC 컨테이너가 관리하는 객체(Bean)들 사이의 의존관계를 자동으로 연결(주입)해주는 것을 말합니다. 개발자는 인터페이스에만 의존하고, 실제 구현체는 Spring이 주입해줍니다. `@Autowired`가 대표적인 예입니다.

이 개념들의 가장 큰 장점은 \*\*느슨한 결합(Loose Coupling)\*\*입니다. 예를 들어, 데이터베이스 기술을 `MyBatis`에서 `JPA`로 변경할 때, 서비스 코드를 수정할 필요 없이 설정 파일에서 주입하는 구현체만 바꾸면 됩니다.

-----

### 회원 관리 예제 코드

IntelliJ에서 Spring Initializr를 통해 'Spring Web', 'Thymeleaf', 'MyBatis Framework', 'MySQL Driver' 의존성을 추가하고 프로젝트를 생성한 후, 아래와 같이 코드를 작성합니다.

#### 1\. build.gradle (의존성 확인)

프로젝트 생성 시 아래와 같은 핵심 의존성들이 포함되어 있는지 확인합니다.

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3' // 버전은 상이할 수 있음
    runtimeOnly 'com.mysql:mysql-connector-j'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

#### 2\. Member (도메인 객체)

회원 정보를 담을 간단한 클래스입니다.

```java
// src/main/java/com/example/member/domain/Member.java
package com.example.member.domain;

public class Member {
    private Long id;
    private String name;

    // Getter and Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

#### 3\. MemberRepository (리포지토리 - 인터페이스)

데이터 저장소에 접근하는 메서드를 정의한 인터페이스입니다. **DI의 핵심**으로, 서비스 계층은 이 인터페이스에만 의존하게 됩니다.

```java
// src/main/java/com/example/member/repository/MemberRepository.java
package com.example.member.repository;

import com.example.member.domain.Member;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
}
```

#### 4\. MemoryMemberRepository (리포지토리 - 구현체 1)

DB 연결 없이 개념 학습을 위해 메모리에 회원을 저장하는 간단한 구현체입니다.

```java
// src/main/java/com/example/member/repository/MemoryMemberRepository.java
package com.example.member.repository;

import com.example.member.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository // Spring 컨테이너에 Bean으로 등록
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
```

  * `@Repository`: 이 클래스를 Spring의 IoC 컨테이너가 관리하는 **Bean**으로 등록합니다. 이제 Spring이 이 객체의 생성과 생명주기를 관리합니다 (IoC).

#### 5\. MemberService (서비스)

회원 가입과 같은 비즈니스 로직을 처리합니다. `MemberRepository`에 의존하지만, 구현체가 아닌 인터페이스에 의존합니다.

```java
// src/main/java/com/example/member/service/MemberService.java
package com.example.member.service;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Spring 컨테이너에 서비스 Bean으로 등록
public class MemberService {

    private final MemberRepository memberRepository;

    // **DI (의존성 주입)**: 생성자를 통해 MemberRepository의 구현체를 주입받음
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }
}
```

  * `@Service`: 이 클래스 또한 Spring Bean으로 등록됩니다.
  * `@Autowired`: \*\*의존성 주입(DI)\*\*의 핵심입니다. 생성자가 호출될 때, Spring 컨테이너는 `MemberRepository` 타입에 맞는 Bean(`MemoryMemberRepository`)을 **자동으로 찾아 주입**해줍니다. 개발자는 `new MemoryMemberRepository()` 코드를 작성할 필요가 없습니다.

#### 6\. MemberController (컨트롤러)

웹 요청을 받아 서비스를 호출하고, 결과를 뷰에 전달합니다.

```java
// src/main/java/com/example/member/controller/MemberController.java
package com.example.member.controller;

import com.example.member.domain.Member;
import com.example.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // Spring 컨테이너에 컨트롤러 Bean으로 등록
public class MemberController {

    private final MemberService memberService;

    // **DI (의존성 주입)**: 생성자를 통해 MemberService Bean을 주입받음
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm"; // templates/members/createMemberForm.html
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/"; // 홈으로 리다이렉트
    }
}

// 간단한 Form DTO
class MemberForm {
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

  * `MemberController`는 `MemberService`가 필요합니다. `@Autowired`를 통해 Spring 컨테이너로부터 `MemberService` Bean을 주입받습니다. `MemberController`는 `MemberService`가 어떻게 생성되는지 알 필요가 없습니다.

#### 7\. Thymeleaf 템플릿

간단한 회원 가입 폼입니다.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>
<div class="container">
    <form action="/members/new" method="post">
        <div class="form-group">
            <label for="name">이름</label>
            <input type="text" id="name" name="name" placeholder="이름을 입력하세요">
        </div>
        <button type="submit">등록</button>
    </form>
</div>
</body>
</html>
```

-----

### IoC와 DI 학습 포인트 정리 ✨

위 코드에서 IoC와 DI가 어떻게 동작하는지 정리해 보겠습니다.

1.  **IoC (제어의 역전)**

      * `@Repository`, `@Service`, `@Controller` 어노테이션이 붙은 클래스들은 개발자가 `new`로 직접 생성하지 않았습니다.
      * 애플리케이션이 실행될 때, Spring 컨테이너가 이 어노테이션들을 스캔하여 해당 클래스의 객체(Bean)를 **직접 생성하고 관리**합니다.
      * 객체 생성과 관리의 제어권이 개발자에서 Spring으로 넘어갔습니다. 이것이 바로 **IoC**입니다.

2.  **DI (의존성 주입)**

      * `MemberService`는 `MemberRepository`가 필요하고, `MemberController`는 `MemberService`가 필요합니다.
      * `@Autowired`를 사용한 생성자를 통해, Spring 컨테이너가 **자동으로** 필요한 의존 객체(Bean)를 찾아서 **주입**해주었습니다.
      * `MemberService`는 `MemoryMemberRepository`라는 구체적인 클래스를 전혀 모르고, 오직 `MemberRepository` 인터페이스만 알고 있습니다. 이것이 **느슨한 결합**의 핵심입니다.

### MyBatis로 전환하기 (DI의 장점)

만약 이제 `MemoryMemberRepository`가 아닌, 실제 MySQL DB를 사용하는 `MyBatisMemberRepository`로 바꾸고 싶다면 어떻게 해야 할까요? **`MemberService` 코드는 단 한 줄도 수정할 필요가 없습니다.**

1.  `MyBatisMemberRepository` 구현체를 만듭니다. (MyBatis XML Mapper 설정 필요)
2.  기존 `MemoryMemberRepository`의 `@Repository`를 주석 처리하거나 삭제합니다.
3.  새로운 `MyBatisMemberRepository`에 `@Repository`를 붙여줍니다.

<!-- end list -->

```java
// 예시: MyBatis 구현체
// @Repository // MemoryMemberRepository의 어노테이션은 주석 처리
public class MemoryMemberRepository implements MemberRepository { ... }


@Repository // MyBatis 구현체에 어노테이션 추가
public class MyBatisMemberRepository implements MemberRepository {
    // MyBatis 로직 구현...
}
```

이제 애플리케이션을 재시작하면, Spring 컨테이너는 `MemberService`에 `MyBatisMemberRepository`를 주입해줍니다.   
이처럼 **DI는 부품을 교체하듯 손쉽게 구현 기술을 변경할 수 있게 해주는 강력한 기능**입니다.

이 예제를 통해 IoC 컨테이너가 어떻게 객체를 관리하고, DI를 통해 어떻게 의존성을 해결하는지 실질적인 감을 잡으셨기를 바랍니다.
