<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table style="border:1px solid black; width:100%; border-collapse:collapse;">
	<thead>
	<tr>
		<th>Flight No</th>
		<th>Origin</th>
		<th>Destination</th>
		<th>Departure</th>
		<th>Price (Economy)</th>
		<th>Price (Business)</th>
		<th>Action</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="flight" items="${flights}">
		<tr>
			<td><c:out value="${flight.flightNumber}"/></td>
			<td><c:out value="${flight.origin}"/></td>
			<td><c:out value="${flight.destination}"/></td>
			<td><c:out value="${departureMap[flight.id]}"/></td>
			<td><c:out value="${flight.priceEconomy}"/></td>
			<td><c:out value="${flight.priceBusiness}"/></td>
			<td>
				<c:choose>
					<c:when test="${not empty sessionScope.userId}">
						<a href="<c:url value='/user/book?flightId=${flight.id}'/>">Book</a>
					</c:when>
					<c:otherwise>
						<a href="<c:url value='/login'/>">Book</a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>