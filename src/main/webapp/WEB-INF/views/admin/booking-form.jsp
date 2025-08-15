<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<h2>Booking</h2>

<form action="${pageContext.request.contextPath}/admin/bookings/update" method="post">
	<input type="hidden" name="id" value="${booking.id}" />
	User: ${booking.user.username} <br/>
	Flight: ${booking.flight.flightNumber} <br/>
	Seats: <input type="number" name="seats" value="${booking.seats}" min="1" /><br/>
	Status:
	<select name="status">
		<option value="BOOKED" ${booking.status == 'BOOKED' ? 'selected' : ''}>BOOKED</option>
		<option value="CANCELLED" ${booking.status == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
	</select><br/>
	<button type="submit">Save</button>
</form>