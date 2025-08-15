<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<h2>Bookings</h2>
<table border="1" cellpadding="6">
	<tr><th>ID</th><th>User</th><th>Flight</th><th>Seats</th><th>Status</th><th>Actions</th></tr>
	<c:forEach var="b" items="${bookings}">
		<tr>
			<td>${b.id}</td>
			<td>${b.user.username}</td>
			<td>${b.flight.flightNumber}</td>
			<td>${b.seats}</td>
			<td>${b.status}</td>
			<td>
				<a href="${pageContext.request.contextPath}/admin/bookings/view/${b.id}">View / Edit</a> |
				<a href="${pageContext.request.contextPath}/admin/bookings/delete/${b.id}" onclick="return confirm('Delete booking?');">Delete</a>
			</td>
		</tr>
	</c:forEach>
</table>