<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Login</title>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>"/>
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>
	<h1>Manage Flights</h1>
	<table style="border:1px solid black">
		<tr>
			<th>ID</th>
			<th>Flight</th>
			<th>Economy Seats</th>
			<th>Business Seats</th>
			<th>Action</th>
		</tr>
		<c:forEach var="b" items="${flights}">
			<tr>
				<td>${b.id}</td>
				<td>${b.flightNumber}</td>
				<td>${b.economySeatsAvailable}/${b.totalEconomySeats}</td>
				<td>${b.businessSeatsAvailable}/${b.totalBusinessSeats}</td>
				<td>
					<form action="${pageContext.request.contextPath}/admin/flights/cancel" method="post">
						<input type="hidden" name="id" value="${b.id}"/>
						<button type="submit">Cancel</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>