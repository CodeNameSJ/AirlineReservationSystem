<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Flight Form</title></head>
<body>
<h1><c:choose><c:when
        test="${flight.id != null}">Edit Flight</c:when><c:otherwise>New Flight</c:otherwise></c:choose></h1>
<form action="${pageContext.request.contextPath}/admin/flights/>" method="post">
    <input type="hidden" name="id" value="${flight.id}"/>
    Flight Number: <label>
    <input name="flightNumber" value="${flight.flightNumber}"/>
</label><br/>
    Origin: <label>
    <input name="origin" value="${flight.origin}"/>
</label><br/>
    Destination: <label>
    <input name="destination" value="${flight.destination}"/>
</label><br/>
    Departure: <label>
    <input type="datetime-local" name="departureTime" value="${flight.departureTime}"/>
</label><br/>
    Total Economy Seats: <label>
    <input type="number" name="totalEconomySeats" value="${flight.totalEconomySeats}"/>
</label><br/>
    Total Business Seats: <label>
    <input type="number" name="totalBusinessSeats" value="${flight.totalBusinessSeats}"/>
</label><br/>
    Price Economy: <label>
    <input name="priceEconomy" value="${flight.priceEconomy}"/>
</label><br/>
    Price Business: <label>
    <input name="priceBusiness" value="${flight.priceBusiness}"/>
</label><br/>
    <button type="submit">Save</button>
</form>
</body>
</html>