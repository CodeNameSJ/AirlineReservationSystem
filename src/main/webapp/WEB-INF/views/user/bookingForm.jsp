<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Book Flight</title>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>">
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>
	<h1>Book Flight</h1>
	<c:if test="${not empty flight}">
		<p>Flight: ${flight.origin} → ${flight.destination}</p>
		<p>Departure: ${flight.departureDisplay}</p>
		<form action="${pageContext.request.contextPath}/user/book" method="post">
			<input type="hidden" name="flightId" value="${flight.id}"/>
			Seats: <label>
			<input type="number" name="seats" value="1" min="1" required/>
		</label>
			<button type="submit">Confirm Booking</button>
		</form>
	</c:if></main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>