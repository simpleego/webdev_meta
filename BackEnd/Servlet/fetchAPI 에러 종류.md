# fetchAPI 에러 종류
> catch 에서 발생하는 에러와 response 발생하는 에러
---

## ⚠️ `catch()`에서 발생하는 에러

### 🔍 의미
- **네트워크 자체의 문제** 또는 **JavaScript 코드 오류**로 인해 요청이 실패한 경우
- 서버에 도달하지 못했거나, 응답을 처리하는 중에 문제가 생긴 경우

### 🧨 예시 상황
- 인터넷 연결 끊김
- 서버 주소 오타 (`fetch("/usre/insert")`)
- CORS 정책 위반
- JSON 파싱 실패 (`response.json()`에서 예외 발생)

### ✅ 코드 예시
```javascript
fetch("/user/insert")
  .then(response => response.json())
  .catch(error => {
    console.error("catch 에러:", error); // 네트워크 또는 코드 오류
  });
```

---

## ❗ `response.ok`로 확인하는 에러

### 🔍 의미
- **서버는 응답했지만**, 상태 코드가 200~299 범위를 벗어난 경우
- 즉, 요청은 성공적으로 도달했지만, 서버가 "요청을 처리할 수 없다"고 응답한 경우

### 🧨 예시 상황
- 400 Bad Request (입력값 누락)
- 401 Unauthorized (로그인 안 됨)
- 404 Not Found (경로 없음)
- 500 Internal Server Error (서버 내부 오류)

### ✅ 코드 예시
```javascript
fetch("/user/insert")
  .then(response => {
    if (!response.ok) {
      throw new Error("서버 오류: " + response.status);
    }
    return response.json();
  })
  .then(data => {
    console.log("정상 응답:", data);
  })
  .catch(error => {
    console.error("catch 에러:", error); // 위에서 throw한 에러도 여기로 옴
  });
```

---

## 🧠 요약 비교

| 구분 | `catch()` | `response.ok` |
|------|-----------|----------------|
| 발생 조건 | 네트워크 오류, 코드 오류 | 서버가 응답했지만 상태 코드가 실패 |
| 서버 도달 여부 | ❌ 도달 못함 또는 응답 처리 실패 | ✅ 도달함 |
| 예시 | DNS 오류, JSON 파싱 실패 | 404, 500, 401 등 |
| 처리 방식 | `catch(error)` | `if (!response.ok)`로 분기 |

---

## 💡 팁

- `catch()`는 **마지막 안전망**이고, `response.ok`는 **서버 응답의 품질 검사**
- 둘 다 함께 쓰는 것이 가장 안전한 방식
