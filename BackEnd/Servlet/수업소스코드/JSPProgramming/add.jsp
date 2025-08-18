<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>덧셈기</title>
</head>
<body>
	<h1>덧셈기</h1>
	<form action="add" >
		<ul>
			<li>숫자1 : <input type="text" name="x" value="0"> </li>
			<li>숫자2 : <input type="text" name="y" value="0"> </li>
		</ul>
		
		<p><input type="submit" name="op" value="+"></p>
		<p><input type="submit" name="op" value="-"></p>
		<p>결과 : <%=request.getAttribute("result") %> </p>
	</form>
</body>
</html>