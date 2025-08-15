<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Manage Flights</title>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>"/>
	<style>
		.warning-row {
			background-color: #fff3cd;
		}

		.warning-row td {
			padding: 10px;
		}
	</style>
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>
	<h1>Manage Flights</h1>
	<table style="border:1px solid black; border-collapse: collapse; width:100%;">
		<tr>
			<th>ID</th>
			<th>Flight</th>
			<th>Origin</th>
			<th>Destination</th>
			<th>Departure</th>
			<th>Arrival</th>
			<th>Economy Seats</th>
			<th>Business Seats</th>
			<th>Action</th>
		</tr>

		<c:forEach var="flight" items="${flights}">
			<!-- Main data row -->
			<tr>
				<td>${flight.id}</td>
				<td>${flight.flightNumber}</td>
				<td>${flight.origin}</td>
				<td>${flight.destination}</td>
				<td>${flight.departureTime}</td>
				<td>${flight.arrivalTime}</td>
				<td>${flight.economySeatsAvailable}/${flight.totalEconomySeats}</td>
				<td>${flight.businessSeatsAvailable}/${flight.totalBusinessSeats}</td>
				<td>
					<!-- Delete triggers the inline confirmation -->
					<form method="get" action="${pageContext.request.contextPath}/admin/flights/edit"
					      style="display:inline;">
						<input type="hidden" name="id" value="${flight.id}"/>
						<button type="submit">Edit</button>
						<button type="button" onclick="toggleWarning(${flight.id}, true)">
							Delete
						</button>
					</form>
				</td>
			</tr>

			<!-- Inline warning row -->
			<tr id="warning-${flight.id}" class="warning-row" style="display:none;">
				<td colspan="5">
					<strong>&#9888;
						Are you sure you want to delete this flight? Deleting it will also remove all related bookings.
					</strong>
					<br/>
					<form method="post" action="${pageContext.request.contextPath}/admin/flights/delete"
					      style="display:inline;">
						<input type="hidden" name="id" value="${flight.id}"/>
						<input type="hidden" name="confirm" value="true"/>
						<button type="submit" style="background-color:red; color:white;">
							Yes, Delete
						</button>
					</form>
					<button type="button" onclick="toggleWarning(${flight.id}, false)">Cancel</button>
				</td>
			</tr>
		</c:forEach>
	</table>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
<script src="<c:url value='../js/confimation.js'/>" defer></script>
</body>
</html>