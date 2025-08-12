<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Admin Dashboard</title>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>">
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>
	<h1>Admin Dashboard</h1>
	<h2>All-Flights Overview</h2>
	<table style="border:1px solid black">
		<tr>
			<th>Flight No</th>
			<th>Origin</th>
			<th>Destination</th>
			<th>Departure</th>
			<th>Arrival</th>
			<th>Economy Seats</th>
			<th>Business Seats</th>
		</tr>
		<c:forEach var="flight" items="${flights}">
			<tr>
				<td>${flight.flightNumber}</td>
				<td>${flight.origin}</td>
				<td>${flight.destination}</td>
				<td>${flight.departureTime}</td>
				<td>${flight.arrivalTime}</td>
				<td>${flight.economySeatsAvailable}/${flight.totalEconomySeats}</td>
				<td>${flight.businessSeatsAvailable}/${flight.totalBusinessSeats}</td>
			</tr>
		</c:forEach>
	</table>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>