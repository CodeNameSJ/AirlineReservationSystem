<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Book Flight</title></head>
<body>
<h1>Book Flight ${flight.flightNumber}</h1>
<form action="<c:url value='/user/book'/>" method="post">
	<input type="hidden" name="flightId" value="${flight.id}"/>
	Class:
	<select name="seatClass">
		<c:forEach var="cls" items="${seatClasses}">
			<option value="${cls}">${cls}</option>
		</c:forEach>
	</select><br/>
	Seats: <input type="number" name="seats" min="1" max="${flight.economySeatsAvailable}"/><br/>
	<button type="submit">Confirm Booking</button>
</form>
</body>
</html>