<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문목록</title>
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
					<th class="w60">주문번호</th>
					<th class="expand">고객아이디</th>
					<th class="expand">도서아이디</th>
					<th class="w100">주문일자</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="n" items="${list }">
					<tr>
						<td>${n.orderid }</td>
						<td><a
							href="/order/detail?id=${n.orderid }&custid=${n.custid }">${n.orderid }</a></td>
						<td>${n.bookid }</td>
						<td>${n.orderDate }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

</body>
</html>