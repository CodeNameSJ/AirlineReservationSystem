<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
	<c:when test="${not empty flights}">
		<h2>Available Flights</h2>

		<table border="1">
			<thead>
			<tr>
				<th>Flight No</th>
				<th>Airline</th>
				<th>From</th>
				<th>To</th>
				<th>Departure</th>
				<th>Arrival</th>
				<th>Price</th>
				<th>Action</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="flight" items="${flights}">
				<tr>
					<td>${flight.flightNumber}</td>
					<td>${flight.airline}</td>
					<td>${flight.source}</td>
					<td>${flight.destination}</td>
					<td>${flight.departureTime}</td>
					<td>${flight.arrivalTime}</td>
					<td>${flight.price}</td>
					<td>
						<a href="${pageContext.request.contextPath}/book/${flight.id}">Book</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<p>No flights available for the selected criteria.</p>
	</c:otherwise>
</c:choose>