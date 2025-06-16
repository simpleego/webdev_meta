# HTML 화면에서 데이터 가져오기

> 화면에서 가져온 모든 것은 1차적으로 **요소 객체**이다. 
> 따라서 반드시 요소가 갖고 있는 고유한 속성(**value,src, innerText, innerHTML...**)을 지정하여 필요한 값을 가져온다.  
> 결론적으로 HTML 태그에 값은 속성으로 존재함으로 그에 맞는 **속성을 설정하여** 가져오면 되는 것이다.


## HTML화면에서 필요한 값을 가져오는 방법의 발전과정 
좋은 질문이에요, jong! HTML 요소의 값을 가져오는 방법은 자바스크립트의 발전과 함께 점차 더 직관적이고 강력하게 발전해왔습니다. 아래에 과거부터 현재까지 널리 사용된 주요 방식들을 시대별 흐름에 따라 정리해드릴게요.

---

### ✅ 1. 전통적인 방식 (1990년대 후반 ~ 2000년대 초)
#### `document.forms` 및 `document.all` 사용
```javascript
// form의 input 접근
const username = document.forms["myForm"]["username"].value;
```
- 한때 `document.all`도 사용됐지만, 지금은 비표준이라 사용하지 않습니다.

---

### ✅ 2. DOM Level 1 방식 (2000년대 중반)
#### `getElementById`, `getElementsByTagName`, `getElementsByName`
```javascript
const name = document.getElementById('username').value;

const inputs = document.getElementsByTagName('input');
const firstInput = inputs[0].value;

const radios = document.getElementsByName('gender');
```
- 여전히 널리 사용되는 방식이에요. ID를 이용한 선택은 특히 안정적이죠.

---

### ✅ 3. DOM Level 2 / 현대적인 방식 (2009~현재)
#### `querySelector`, `querySelectorAll`
```javascript
const name = document.querySelector('#username').value;
const checkedRadio = document.querySelector('input[name="gender"]:checked').value;
const checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
```
- CSS 선택자 문법을 그대로 사용 가능해서 매우 유연하고 직관적입니다.

---

### ✅ 4. HTMLFormElement와 `elements` 속성 활용
```javascript
const form = document.getElementById('myForm');
const username = form.elements['username'].value;
```
- 전체 폼을 다루거나 유효성 검사를 할 때 매우 유용합니다.

---

### ✅ 5. 최근의 발전된 접근 방법 (ES6 이후)
#### `Array.from()`과 `forEach`, 구조 분해 등과 조합
```javascript
const selectedOptions = Array.from(document.querySelector('#multiSelect').selectedOptions).map(opt => opt.value);

const { username, email } = form.elements;
console.log(username.value, email.value);
```
- 반복 작업을 깔끔하게 처리하고, 여러 입력 요소를 손쉽게 다룰 수 있게 해줍니다.

---
좋아요 jong! `Array.from()`과 `forEach()`를 조합하면 반복 처리나 선택된 HTML 요소들의 값을 수집할 때 매우 유용하죠. 실제 예제를 통해 보여드릴게요.

---

### ✅ 예제 1: 체크박스 여러 개 선택한 값 출력하기
```html
<form id="hobbyForm">
  <label><input type="checkbox" name="hobby" value="reading" checked> 독서</label>
  <label><input type="checkbox" name="hobby" value="music"> 음악</label>
  <label><input type="checkbox" name="hobby" value="sports" checked> 운동</label>
</form>

<script>
  const checkboxes = document.querySelectorAll('input[name="hobby"]:checked');

  const selected = Array.from(checkboxes);  // NodeList를 배열로 변환
  selected.forEach(checkbox => {
    console.log(checkbox.value);  // "reading", "sports"
  });
</script>
```

---

### ✅ 예제 2: `<select multiple>`에서 선택된 항목들을 출력하기
```html
<select id="colors" multiple>
  <option value="red" selected>빨강</option>
  <option value="green">초록</option>
  <option value="blue" selected>파랑</option>
</select>

<script>
  const selectEl = document.getElementById('colors');
  const selectedOptions = Array.from(selectEl.selectedOptions);

  selectedOptions.forEach(option => {
    console.log(option.value);  // "red", "blue"
  });
</script>
```

---

### ✅ 예제 3: 여러 `<input>` 필드 값을 한 번에 가져오기
```html
<form id="userForm">
  <input type="text" name="username" value="홍길동">
  <input type="email" name="email" value="hong@example.com">
  <input type="number" name="age" value="30">
</form>

<script>
  const inputs = document.querySelectorAll('#userForm input');

  Array.from(inputs).forEach(input => {
    console.log(`${input.name}: ${input.value}`);
    // 예시 출력: username: 홍길동, email: hong@example.com, age: 30
  });
</script>
```

---

### 1. `<input type="text">` – 일반 텍스트 입력
```html
<input type="text" id="username" value="홍길동">
<input type="text" id="num">
<script>
  const username = document.getElementById('username').value;
  const $num = document.querySelector('#num');  // 요소를 객체로 가져오기
  let num =  parseInt($num.value);    // 요소객체의 속성을 이용하여 값을 가져오기
                                        // 추가적으로 필요한 형태로 변환(숫자)
</script>
```

### 2. `<input type="password">` – 비밀번호 입력
```html
<input type="password" id="pwd" value="1234">
<script>
  const password = document.getElementById('pwd').value;
</script>
```

### 3. `<input type="email">` – 이메일 입력
```html
<input type="email" id="email" value="test@example.com">
<script>
  const email = document.getElementById('email').value;
</script>
```

### 4. `<input type="radio">` – 라디오 버튼
```html
<input type="radio" name="gender" value="male" checked>남성
<input type="radio" name="gender" value="female">여성
<script>
  const gender = document.querySelector('input[name="gender"]:checked').value;
</script>
```
```html
---
반복문 for를 이용한 방법
```
<h2>색깔</h2>
<label>빨강 : <input type="checkbox" name="color" value="빨강"></label>
<label></label>주황 : <input type="checkbox" name="color" value="주황"></label>
<label></label>노랑 : <input type="checkbox" name="color" value="노랑"></label>
<label></label>초록 : <input type="checkbox" name="color" value="초록"></label>
<label></label>파랑 : <input type="checkbox" name="color" value="파랑"></label>
<p>
    <label>선택한 색깔 :
        <span id="color"></span>
    </label>
</p>
<button onclick="userSelection()">선택 확인</button>
<script>
 // checkbox 값 가져오기
let color = document.getElementsByName('color');
let colorChoice=''; // 여기에 선택된 radio 버튼의 값이 담기게 된다.
for (let i = 0; i < color.length; i++) {
    if (color[i].checked) {
        colorChoice += color[i].value + ' ';
    }
}
document.getElementById("color").innerHTML = colorChoice;
</script>
```
```

### 5. `<input type="checkbox">` – 체크박스
```html
<input type="checkbox" id="agree" checked>약관 동의
<script>
  const isAgreed = document.getElementById('agree').checked;  // true or false
</script>
```
---
```javascript
// 라디오 값 가져오기
<script>
let gender = document.getElementsByName('gender');
let selectedGender; // 여기에 선택된 radio 버튼의 값이 담기게 된다.

for (let i = 0; i < gender.length; i++) {
    if (gender[i].checked) {
        selectedGender = gender[i].value;
    }
}
</script>
```

### 6. `<input type="file">` – 파일 업로드
```html
<input type="file" id="upload">
<script>
  const fileInput = document.getElementById('upload').files[0];
</script>
```

### 7. `<textarea>` – 여러 줄 텍스트 입력
```html
<textarea id="comment">댓글을 입력하세요</textarea>
<script>
  const comment = document.getElementById('comment').value;
</script>
```

### 8. `<select>` – 드롭다운 목록
```html
<select id="city">
  <option value="seoul">서울</option>
  <option value="daejeon" selected>대전</option>
</select>
<script>
  const selectedCity = document.getElementById('city').value;
</script>
```

### 9. `<input type="date">`, `<input type="number">`, 등 기타 타입들
```html
<input type="date" id="birth" value="2000-01-01">
<input type="number" id="age" value="25">
<script>
  const birth = document.getElementById('birth').value;
  const age = parseInt(document.getElementById('age').value, 10);
</script>
```
### 10. `<textarea>` 태그
```html
<textarea id="message">안녕하세요</textarea>
<script>
  const message = document.getElementById('message').value;
  console.log(message);  // "안녕하세요"
</script>
```

### 11. `<select>` 태그 (단일선택) --> radio와 같은 선택
```html
<select id="fruit">
  <option value="apple">사과</option>
  <option value="banana" selected>바나나</option>
</select>
<script>
  const selectedFruit = document.getElementById('fruit').value;
  console.log(selectedFruit);  // "banana"
</script>
```
### 12. `<select>` 태그 multiple(다중선택) --> checkbox와 같은 선택
```html
<select id="colors" multiple>
  <option value="red" selected>빨강</option>
  <option value="green">초록</option>
  <option value="blue" selected>파랑</option>
</select>
```
```javascript
<script>
  const selectEl = document.getElementById('colors');
  const selectedColors = Array.from(selectEl.selectedOptions).map(option => option.value);
  console.log(selectedColors);  // ["red", "blue"]
</script>
```

### 13. `<p>` 또는 `<div>` 같은 텍스트를 표시하는 태그
```html
<p id="desc">이것은 설명입니다.</p>
<script>
  const description = document.getElementById('desc').textContent;
  console.log(description);  // "이것은 설명입니다."
</script>
```

### 14. `<span>` 태그
```html
<span id="nickname">하늘색고래</span>
<script>
  const nickname = document.getElementById('nickname').innerText;
  console.log(nickname);  // "하늘색고래"
</script>
```

### 15. `<img>` 태그 (예: 이미지의 주소 가져오기)
```html
<img id="mainImg" src="image.jpg" alt="대표 이미지">
<script>
  const imageUrl = document.getElementById('mainImg').src;
  console.log(imageUrl);  // "image.jpg"
</script>
```
