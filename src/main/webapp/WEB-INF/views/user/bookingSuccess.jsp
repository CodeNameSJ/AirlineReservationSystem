<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Booking Confirmation</title></head>
<body>
<h1>Booking Confirmed</h1>
<p>Booking ID: ${booking.id}</p>
<p>Flight: ${booking.flight.flightNumber}</p>
<p>Class: ${booking.seatClass}</p>
<p>Seats: ${booking.seats}</p>
<p>Status: ${booking.status}</p>
<a href="${pageContext.request.contextPath}/user/bookings/>">View My Bookings</a>
</body>
</html>