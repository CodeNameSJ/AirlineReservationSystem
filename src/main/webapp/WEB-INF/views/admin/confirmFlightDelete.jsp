<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Confirm Flight Deletion</title>
</head>
<body>
<h2>Flight Deletion Confirmation</h2>

<c:if test="${hasBookings}">
	<p>⚠ This flight has existing bookings!</p>
	<p>If you delete it, all related bookings will also be deleted.<br/>
		Flight: <strong>${flight.flightNumber}</strong> - ${flight.origin} ➡ ${flight.destination}
	</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/admin/flights/delete">
	<input type="hidden" name="id" value="${flightId}" />
	<input type="hidden" name="confirm" value="true" />
	<button type="submit" style="background-color: red; color: white;">
		Yes, Delete Flight and All Bookings
	</button>
	<a href="${pageContext.request.contextPath}/admin/flights">Cancel</a>
</form>
</body>
</html>