<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Airline Reservation - Home</title>
	<link rel="stylesheet" href="<c:url value='./css/style.css'/>">
</head>
<body>
<h1>Welcome to Airline Reservation System</h1>
<a href="<c:url value='./loginUser'/>">User Login</a> |
<a href="<c:url value='./register'/>">Register</a> |
<a href="<c:url value='./loginAdmin'/>">Admin Login</a>

<h2>All Flights</h2>
<table border="1">
	<tr><th>Flight No</th><th>Origin</th><th>Destination</th><th>Departure</th><th>Economy Seats</th><th>Business Seats</th><th>Actions</th></tr>
	<c:forEach var="flight" items="${flights}">
		<tr>
			<td>${flight.flightNumber}</td>
			<td>${flight.origin}</td>
			<td>${flight.destination}</td>
			<td>${flight.departureTime}</td>
			<td>${flight.economySeatsAvailable}</td>
			<td>${flight.businessSeatsAvailable}</td>
			<td><a href="<c:url value='/flights?origin=&destination=&date='/>">Search</a></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>