<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Booking Success</title>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>">
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>
	<nav>
		<a href="${pageContext.request.contextPath}/">Home</a> | <a
			href="${pageContext.request.contextPath}/user/home">My
		Bookings</a>
	</nav>

	<h1>Booking Confirmed</h1>
	<c:if test="${not empty booking}">
		<p>Your booking ID is: <strong>${booking.id}</strong></p>
		<p>Flight: ${booking.flight.flightNumber}</p>
		<p>Departure: ${booking.flight.origin} → ${booking.flight.destination}</p>
		<p>Seats: ${booking.seats}</p>
		<p>Status: ${booking.status}</p>
	</c:if>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>