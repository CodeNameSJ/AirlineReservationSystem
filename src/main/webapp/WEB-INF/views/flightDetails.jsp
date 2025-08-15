<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Flight Details</title>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>"/>
</head>
<body>
<header><jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/></header>
<main>
	<h1>Flight ${flight.flightNumber}</h1>

	<dl>
		<dt>Origin</dt><dd><c:out value="${flight.origin}"/></dd>
		<dt>Destination</dt><dd><c:out value="${flight.destination}"/></dd>
		<dt>Departure</dt><dd><c:out value="${departureMap}"/></dd>
		<dt>Economy seats available</dt><dd><c:out value="${flight.economySeatsAvailable}"/></dd>
		<dt>Business seats available</dt><dd><c:out value="${flight.businessSeatsAvailable}"/></dd>
		<dt>Price (Economy)</dt><dd><c:out value="${flight.priceEconomy}"/></dd>
		<dt>Price (Business)</dt><dd><c:out value="${flight.priceBusiness}"/></dd>
	</dl>

	<c:choose>
		<c:when test="${not empty sessionScope.userId}">
			<a href="<c:url value='/user/book?flightId=${flight.id}'/>">Book this flight</a>
		</c:when>
		<c:otherwise>
			<a href="<c:url value='/login'/>">Login to Book</a>
		</c:otherwise>
	</c:choose>
</main>
<footer><jsp:include page="/WEB-INF/views/fragments/footer.jsp"/></footer>
</body>
</html>