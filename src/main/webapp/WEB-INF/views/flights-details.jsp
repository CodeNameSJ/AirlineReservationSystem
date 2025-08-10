<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Flight Details</title>
	<link rel="stylesheet" href="<c:url value='./css/style.css'/>">
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>

	<h1>Flight Details</h1>
	<c:if test="${not empty flight}">
		<p>From: ${flight.origin}</p>
		<p>To: ${flight.destination}</p>
		<p>Departure: ${flight.departureTime}</p>
		<p>Arrival: ${flight.arrivalTime}</p>
		<p>Price: ₹${flight.price}</p>

		<c:choose>
			<c:when test="${not empty sessionScope.userId}">
				<a href="${pageContext.request.contextPath}/user/book?flightId=${flight.id}">Book</a>
			</c:when>
			<c:otherwise>
				<a href="${pageContext.request.contextPath}/login">Login to Book</a>
			</c:otherwise>
		</c:choose>

	</c:if></main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>