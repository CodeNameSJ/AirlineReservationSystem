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
			<th>User</th>
			<th>Flight</th>
			<th>Seats</th>
			<th>Status</th>
			<th>Action</th>
		</tr>
		<c:forEach var="b" items="${flights}">
			<tr>
				<td>${b.id}</td>
				<td>${b.flight.flightNumber}</td>
				<td>${b.seats}</td>
				<td>${b.status}</td>
				<td>
						<form action="${pageContext.request.contextPath}/admin/flights/cancel" method="post">
							<input type="hidden" name="id" value="${b.id}"/>
							<button type="submit">Cancel</button>
						</form>
				</td>
			</tr>
		</c:forEach>
	</table>

	<h2>All Flights</h2>
	<table style="border:1px solid black">
		<tr>
			<th>Flight No</th>
			<th>Origin</th>
			<th>Destination</th>
			<th>Departure</th>
			<th>Economy Seats</th>
			<th>Business Seats</th>
			<th>Actions</th>
		</tr>
		<c:forEach var="flight" items="${flights}">
			<tr>
				<td>${flight.flightNumber}</td>
				<td>${flight.origin}</td>
				<td>${flight.destination}</td>
				<td>${flight.departureTime}</td>
				<td>${flight.economySeatsAvailable}</td>
				<td>${flight.businessSeatsAvailable}</td>
				<td><a href="${pageContext.request.contextPath}/flights?origin=&destination=&date=">Search</a></td>
			</tr>
		</c:forEach>
	</table>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>