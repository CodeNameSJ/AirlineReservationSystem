<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Manage Bookings</title>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>">
	<style>
		button {
			border: none;
			border-radius: 1rem;
			transition: all 0.5s linear;
			padding: 1.2em;
			background-color: #ACB1D6;
		}

		.tooltip {
			position: relative;
			display: inline-block;
		}

		.tooltip .tooltiptext {
			visibility: hidden;
			background-color: #AAAAAA;
			color: white;
			border-radius: 10px;
			padding: 5px;
			position: absolute;
			z-index: 1;
			left: 62px;
			width: 60px;
		}

		.tooltip:hover .tooltiptext {
			visibility: visible;
		}
	</style>
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>
	<h1>Manage Bookings</h1>
	<table style="border:1px solid black">
		<tr>
			<th>ID</th>
			<th>User</th>
			<th>Flight</th>
			<th>Seats</th>
			<th>Date</th>
			<th>Status</th>
			<th>Action</th>
		</tr>
		<c:forEach var="b" items="${bookings}">
			<tr>
				<td>${b.id}</td>
				<td>${b.user.username}</td>
				<td>${b.flight.flightNumber}</td>
				<td>${b.seats}</td>
				<td>${b.bookingTime}</td>
				<td>${b.status}</td>
				<td>
					<c:if test="${b.status == 'BOOKED'}">
						<form action="${pageContext.request.contextPath}/admin/bookings/cancel" method="post" style="display:inline;">
							<input type="hidden" name="id" value="${b.id}"/>
							<button class="tooltip" type="submit">
								<span class="tooltip-text">Cancel</span>
							</button>
						</form>
					</c:if>

					<form action="${pageContext.request.contextPath}/admin/bookings/delete/${b.id}" method="get" style="display:inline;">
						<button class="tooltip" type="submit">
							<span class="tooltip-text">Delete</span>
						</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>