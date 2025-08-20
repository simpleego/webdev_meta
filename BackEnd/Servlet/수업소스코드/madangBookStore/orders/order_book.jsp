<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 주문</title>
<style>
	#container {
		width: 600px;
		margin: 10px auto;
		padding: 10px;
		border: 10px solid #ddd;
	}
</style>
</head>
<body>
	<div id="container">
		<form action="order/book" method="post">
			아이디: <input type="text" name="custid" readonly="readonly" value="${custid }"><br>
			책이름 : <input type="text" name="bookname" readonly="readonly" value="${bookname }"><br>
			정가 : <input type="text" name="price" readonly="readonly" value="${price }"><br>
			판매가 : <input type="text" name="saleprice" readonly value="${saleprice }"><br>			
			<input type="submit" value="회원가입">	
		</form>	
	
	</div>
			
</body>
</html>