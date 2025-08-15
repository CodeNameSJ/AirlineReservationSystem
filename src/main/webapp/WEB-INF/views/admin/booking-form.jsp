<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Bookings Details</title>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>"/>
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>
	<h2>Booking</h2>

	<form action="${pageContext.request.contextPath}/admin/bookings/update" method="post">
		<input type="hidden" name="id" value="${booking.id}"/>
		User: ${booking.user.username} <br/>
		Flight: ${booking.flight.flightNumber} <br/>
		Seats: <label>
		<input type="number" name="seats" value="${booking.seats}" min="1"/>
	</label><br/>
		Status:
		<label>
			<select name="status">
				<option value="BOOKED" ${booking.status == 'BOOKED' ? 'selected' : ''}>BOOKED</option>
				<option value="CANCELLED" ${booking.status == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
			</select>
		</label><br/>
		<button type="submit">Save</button>
	</form>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
<script src="<c:url value='../js/confimation.js'/>" defer></script>
</body>
</html>