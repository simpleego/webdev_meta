<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="spag">
		<input type="text" name="n">
		<input type="submit">
	</form>
	
	<%
	pageContext.setAttribute("result", "hello");
	%>
	<%=request.getAttribute("result") %>입니다.
	
	${result}<br>
	
	${requestScope.result}<br>
	
	${names[1]}<br>
	
	${notice.title}<br>
	${notice.name}<br>
	
	${empty param.n?'값이 비어있습니다.':param.n}<br>
	${param.n/2}<br>
	
	${header.accept}<br> 

</body>
</html>