<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Airline Reservation - Home</title>
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

       .tooltip .tooltip-text {
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
	<h2>Welcome, ${sessionScope.username}</h2>

	<h3>Your Bookings</h3>
	<table style="border:1px solid black">
		<tr>
			<th>ID</th>
			<th>Flight</th>
			<th>Class</th>
			<th>Seats</th>
			<th>Status</th>
			<th>Action</th>
		</tr>
		<c:forEach var="b" items="${bookings}">
			<tr>
				<td>${b.id}</td>
				<td>${b.flight.flightNumber}</td>
				<td>${b.seatClass}</td>
				<td>${b.seats}</td>
				<td>${b.status}</td>
				<td>
					<c:if test="${b.status == 'BOOKED'}">
						<form action="${pageContext.request.contextPath}/user/bookings/cancel" method="post">
							<input type="hidden" name="id" value="${b.id}"/>
							<button class="tooltip" type="submit">
								<span class="tooltip-text">Cancel</span>
								<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
								     stroke="currentColor" width="24" height="24">
									<path stroke-linecap="round" stroke-linejoin="round"
									      d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"></path>
								</svg>
							</button>
						</form>
					</c:if>
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