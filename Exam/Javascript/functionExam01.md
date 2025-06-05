```html

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>계산기</title>
    <script>
      **// 여기에 코드 작성**
    </script>
</head>
<body>
    <h2>사칙연산 계산기</h2>
    <input type="number" id="num1" placeholder="첫 번째 숫자">
    
    <div>
        <label><input type="radio" name="operator" checked value="+"> +</label>
        <label><input type="radio" name="operator" value="-"> -</label>
        <label><input type="radio" name="operator" value="*"> *</label>
        <label><input type="radio" name="operator" value="/"> /</label>
    </div>
    
    <input type="number" id="num2" placeholder="두 번째 숫자">
    <button onclick="performCalculation()">계산하기</button>
    <p id="result"></p>
</body>
</html>
```
