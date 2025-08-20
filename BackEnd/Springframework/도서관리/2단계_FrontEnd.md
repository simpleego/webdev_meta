# 프론트엔드 
- Svelte, Axios

---

## 🧭 전체 연동 구조

```
[Svelte Frontend] ←→ [Spring REST API] ←→ [MySQL DB]
```

---

## ⚙️ Svelte 개발 환경 설정

### 1️⃣ 프로젝트 생성

```bash
npm create vite@latest bookstore-svelte -- --template svelte
cd bookstore-svelte
npm install
npm install axios
```

---

## 📁 폴더 구조 예시

```
src/
├── routes/
│   ├── BookList.svelte
│   ├── OrderForm.svelte
│   ├── Login.svelte
│   └── AdminDashboard.svelte
├── App.svelte
└── main.js
```

---

## 📚 도서 목록 컴포넌트 (BookList.svelte)

```svelte
<script>
  import { onMount } from 'svelte';
  import axios from 'axios';

  let books = [];

  onMount(async () => {
    const res = await axios.get('/api/books');
    books = res.data;
  });
</script>

<h2>📚 도서 목록</h2>
<table>
  <thead>
    <tr><th>ID</th><th>제목</th><th>출판사</th><th>가격</th></tr>
  </thead>
  <tbody>
    {#each books as book}
      <tr>
        <td>{book.id}</td>
        <td>{book.bookname}</td>
        <td>{book.publisher}</td>
        <td>{book.price}</td>
      </tr>
    {/each}
  </tbody>
</table>
```

---

## 🛒 주문 생성 컴포넌트 (OrderForm.svelte)

```svelte
<script>
  import axios from 'axios';
  let bookId = '';
  let quantity = 1;
  let customerId = 1;

  async function submitOrder() {
    await axios.post('/api/orders', {
      customerId,
      bookId,
      quantity
    });
    alert('주문 완료!');
  }
</script>

<h3>🛒 도서 주문</h3>
<input type="number" bind:value={bookId} placeholder="도서 ID" />
<input type="number" bind:value={quantity} placeholder="수량" />
<button on:click={submitOrder}>주문하기</button>
```

---

## 🔐 로그인 처리 (Login.svelte)

```svelte
<script>
  import axios from 'axios';
  let username = '';
  let password = '';

  async function login() {
    try {
      const res = await axios.post('/api/customers/login', { username, password });
      alert('로그인 성공!');
      // 사용자 정보 저장 또는 페이지 이동
    } catch {
      alert('로그인 실패');
    }
  }
</script>

<h2>🔐 로그인</h2>
<input type="text" bind:value={username} placeholder="아이디" />
<input type="password" bind:value={password} placeholder="비밀번호" />
<button on:click={login}>로그인</button>
```

---

## 🌐 백엔드 연동 팁

- `vite.config.js`에 proxy 설정 추가:

```js
export default {
  server: {
    proxy: {
      '/api': 'http://localhost:8080'
    }
  }
};
```

- Spring Boot에서 CORS 허용:

```java
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/books")
public class BookApiController { ... }
```

---

## 🎨 UI 개선 제안

- SvelteKit으로 라우팅 및 SSR 확장 가능  
- TailwindCSS로 빠른 스타일링  
- `svelte-chartjs`로 관리자 통계 시각화

---
