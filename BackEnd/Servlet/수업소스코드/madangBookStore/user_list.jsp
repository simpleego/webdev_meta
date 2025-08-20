<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
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
		<table class="table">
			<thead>
				<tr>
					<th class="w60">아이디</th>
					<th class="expand">이름</th>
					<th class="w100">주소</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="n" items="${list }">
					<tr>
						<td>${n.custid }</td>
						<td><a
							href="/customer/detail?id=${n.custid }">${n.name }</a></td>
						<td>${n.address }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

</body>
</html>