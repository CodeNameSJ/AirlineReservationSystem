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
			padding: 0.8em 1.2em;
			background-color: #ACB1D6;
			cursor: pointer;
		}

		.warning-row {
			background-color: #fff3cd;
		}

		.warning-row td {
			padding: 10px;
		}

		.tooltip {
			position: relative;
			display: inline-block;
		}

		.tooltip .tooltip-text {
			visibility: hidden;
			background-color: #AAAAAA;
			color: white;
			border-radius: 10px;
			padding: 5px 8px;
			position: absolute;
			z-index: 1;
			left: 50%;
			transform: translateX(-50%);
			bottom: 130%;
			white-space: nowrap;
		}

		.tooltip:hover .tooltip-text {
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

	<%--	<c:if test="${not empty successMessage}">--%>
	<%--		<div class="flash-success">${successMessage}</div>--%>
	<%--	</c:if>--%>

	<table style="border:1px solid black; border-collapse: collapse; width:100%;">
		<thead>
		<tr>
			<th>ID</th>
			<th>User</th>
			<th>Flight</th>
			<th>Seats</th>
			<th>Date</th>
			<th>Status</th>
			<th style="width:220px;">Action</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="b" items="${bookings}">
			<!-- Main row -->
			<tr>
				<td><c:out value="${b.id}"/></td>
				<td><c:out value="${b.user.username}"/></td>
				<td><c:out value="${b.flight.flightNumber}"/></td>
				<td><c:out value="${b.seats}"/></td>
				<td><c:out value="${b.bookingTime}"/></td>
				<td><c:out value="${b.status}"/></td>
				<td>
					<c:if test="${b.status == 'BOOKED'}">
						<button class="tooltip" type="button" onclick="toggleWarning(${b.id}, true)">
							<span class="tooltip-text">Cancel</span>
							Cancel
						</button>
					</c:if>
					<button class="tooltip" type="button" onclick="toggleWarning(${b.id}, true)">
						<span class="tooltip-text">Delete</span>
						Delete
					</button>
				</td>
			</tr>

			<!-- Inline confirmation row -->
			<tr id="warning-${b.id}" class="warning-row" style="display:none;">
				<!-- colspan = number of table columns -->
				<td colspan="7">
					<strong>&#9888;
						<c:choose>
							<c:when test="${b.status == 'BOOKED'}">
								This booking is still active. You can cancel it (restore seats) or delete it permanently.
							</c:when>
							<c:otherwise>
								This will permanently delete the booking. Are you sure?
							</c:otherwise>
						</c:choose>
					</strong>
					<br/><br/>

					<c:if test="${b.status == 'BOOKED'}">
						<form method="post" action="${pageContext.request.contextPath}/admin/bookings/cancel"
						      style="display:inline;">
							<input type="hidden" name="id" value="${b.id}"/>
							<input type="hidden" name="confirm" value="true"/>
							<button type="submit" style="background-color:#f0ad4e; color:white;">Yes, Cancel</button>
						</form>
					</c:if>

					<form method="post" action="${pageContext.request.contextPath}/admin/bookings/delete"
					      style="display:inline;">
						<input type="hidden" name="id" value="${b.id}"/>
						<input type="hidden" name="confirm" value="true"/>
						<button type="submit" style="background-color:#d9534f; color:white;">Yes, Delete</button>
					</form>

					<button type="button" onclick="toggleWarning(${b.id}, false)">No, Keep</button>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
<script src="<c:url value='../js/confimation.js'/>" defer></script>
</body>
</html>