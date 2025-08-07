<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>My Bookings</title></head>
<body>
<h1>My Bookings</h1>
<table border="1">
	<tr><th>ID</th><th>Flight</th><th>Class</th><th>Seats</th><th>Status</th><th>Action</th></tr>
	<c:forEach var="b" items="${bookings}">
		<tr>
			<td>${b.id}</td>
			<td>${b.flight.flightNumber}</td>
			<td>${b.seatClass}</td>
			<td>${b.seats}</td>
			<td>${b.status}</td>
			<td>
				<c:if test="${b.status == 'BOOKED'}">
					<form action="<c:url value='/user/cancel'/>" method="post">
						<input type="hidden" name="bookingId" value="${b.id}"/>
						<button type="submit">Cancel</button>
					</form>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>