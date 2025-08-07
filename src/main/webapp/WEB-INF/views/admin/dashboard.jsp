<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Admin Dashboard</title></head>
<body>
<h1>Admin Dashboard</h1>
<a href="${pageContext.request.contextPath}/admin/flights/>">Manage Flights</a> |
<a href="${pageContext.request.contextPath}/admin/booking'/>">Manage Bookings</a>
<h2>All-Flights Overview</h2>
<table border="1">
	<tr><th>Flight No</th><th>Origin</th><th>Destination</th></tr>
	<c:forEach var="f" items="${flights}">
		<tr>
			<td>${f.flightNumber}</td><td>${f.origin}</td><td>${f.destination}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>