<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Flight Form</title></head>
<body>
<h1><c:choose><c:when test="${flight.id != null}">Edit Flight</c:when><c:otherwise>New Flight</c:otherwise></c:choose></h1>
<form action="<c:url value='/admin/flights'/>" method="post">
	<input type="hidden" name="id" value="${flight.id}"/>
	Flight Number: <input name="flightNumber" value="${flight.flightNumber}"/><br/>
	Origin: <input name="origin" value="${flight.origin}"/><br/>
	Destination: <input name="destination" value="${flight.destination}"/><br/>
	Departure: <input type="datetime-local" name="departureTime" value="${flight.departureTime}"/><br/>
	Total Economy Seats: <input type="number" name="totalEconomySeats" value="${flight.totalEconomySeats}"/><br/>
	Total Business Seats: <input type="number" name="totalBusinessSeats" value="${flight.totalBusinessSeats}"/><br/>
	Price Economy: <input name="priceEconomy" value="${flight.priceEconomy}"/><br/>
	Price Business: <input name="priceBusiness" value="${flight.priceBusiness}"/><br/>
	<button type="submit">Save</button>
</form>
</body>
</html>