<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Manage Bookings</title></head>
<body>
<h1>Manage Bookings</h1>
<table style="border:1px solid black">
	<tr>
		<th>ID</th>
		<th>User</th>
		<th>Flight</th>
		<th>Seats</th>
		<th>Status</th>
		<th>Action</th>
	</tr>
	<c:forEach var="b" items="${bookings}">
		<tr>
			<td>${b.id}</td>
			<td>${b.user.username}</td>
			<td>${b.flight.flightNumber}</td>
			<td>${b.seats}</td>
			<td>${b.status}</td>
			<td>
				<c:if test="${b.status == 'BOOKED'}">
					<form action="${pageContext.request.contextPath}/admin/bookings/cancel" method="post">
						<input type="hidden" name="id" value="${b.id}"/>
						<button type="submit">Cancel</button>
					</form>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>