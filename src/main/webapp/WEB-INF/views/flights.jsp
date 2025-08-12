<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Search Flights</title>
	<link rel="stylesheet" href="<c:url value='./css/style.css'/>">
	<link rel="stylesheet" href="<c:url value='./css/card.css'/>">
	<link rel="stylesheet" href="<c:url value='../css/search.css'/>">
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>
	<script>
		document.querySelector('#flightSearchForm').addEventListener('submit', function (e) {
			e.preventDefault();
			const form = e.target;
			const params = new URLSearchParams(new FormData(form));
			params.set('ajax', 'true');
			fetch(form.action + '?' + params.toString(), {headers: {'X-Requested-With': 'XMLHttpRequest'}})
					.then(r => r.text())
					.then(html => {
						document.getElementById('searchResults').innerHTML = html;
					})
					.catch(err => console.error(err));
		});
	</script>

	<h1>Search</h1>

	<jsp:include page="/WEB-INF/views/fragments/searchForm.jsp"/>

	<br/>
	<hr/>

	<table style="border:1px solid black">
		<tr>
			<th>Flight No</th>
			<th>Origin</th>
			<th>Destination</th>
			<th>Departure</th>
			<th>Price (Economy)</th>
			<th>Price (Business)</th>
			<th>Action</th>
		</tr>

		<c:forEach var="flight" items="${flights}">
			<tr>
				<td>${flight.flightNumber}</td>
				<td>${flight.origin}</td>
				<td>${flight.destination}</td>
				<td>${flight.departureTime}</td>
				<td>${flight.priceEconomy}</td>
				<td>${flight.priceBusiness}</td>
				<td>

					<c:choose>
						<c:when test="${not empty sessionScope.userId}">
							<a href="${pageContext.request.contextPath}/user/book?flightId=${flight.id}">Book</a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/login">Book</a>
						</c:otherwise>
					</c:choose>
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