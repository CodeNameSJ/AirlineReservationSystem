<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head><title>Book Flight</title></head>
<body>
<%@ include file="../fragments/header.jsp" %>

<h2>Book Flight: ${flight.origin} → ${flight.destination}</h2>
<p>
  Departure: ${flight.departureDate} &#9679; ${flight.departureTime}<br/>
  Arrival:   ${flight.arrivalDate} &#9679; ${flight.arrivalTime}<br/>
  Price per seat: &#8377;${flight.price}<br/>
  Seats available: ${flight.capacity - fn:length(flight.capacity - bookingService.totalSeatsBookedByFlight(flight))} <!-- optional dynamic calc -->
</p>

<form:form method="post" action="${pageContext.request.contextPath}/booking">
  <form:hidden path="seats" value="" /> <!-- to bind form object -->
  <input type="hidden" name="flightId" value="${flight.id}"/>

  <div>
    <label for="seats">Number of seats:</label>
    <form:input path="seats" id="seats"/>
    <form:errors path="seats" cssClass="error"/>
  </div>

  <button type="submit">Confirm Booking</button>
</form:form>

<%@ include file="../fragments/footer.jsp" %>
</body>
</html>
