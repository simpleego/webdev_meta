<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 상세</title>
<link rel="stylesheet" href="/css/style.css">
</head>
<body>
	<div id="container">
		<form action="/book/update" method="post">
			아이디: <input type="text" name="bookid"  readonly value="${book.bookid }"><br>
			도서명 : <input type="text" name="bookname" value="${book.bookname }"><br>
			출판사 : <input type="text" name="publisher" value="${book.publisher }"><br>
			가격 : <input type="text" name="price" value="${book.price }"><br>	
			<input type="submit" value="도서수정">	
			<a class="btn" href="/book/delete?id=${book.bookid }">도서삭제</a>
			
		</form>	
	</div>
</body>
</html>