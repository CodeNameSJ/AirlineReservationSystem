<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Search Flights</title>

	<!-- use absolute context paths -->
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>">
	<link rel="stylesheet" href="<c:url value='../css/card.css'/>">
	<link rel="stylesheet" href="<c:url value='../css/search.css'/>">
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>

<main>
	<h1 class="page-title">Search Flights</h1>
	<jsp:include page="/WEB-INF/views/fragments/searchForm.jsp"/>

	<br/>
	<hr/>

	<!-- Result container: will be replaced by AJAX fragment when searching -->
	<div id="searchResults">
		<table style="border:1px solid black; width:100%; border-collapse:collapse;">
			<thead>
			<tr>
				<th>Flight No</th>
				<th>Origin</th>
				<th>Destination</th>
				<th>Departure</th>
				<th>Price (Economy)</th>
				<th>Price (Business)</th>
				<th>Action</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="flight" items="${flights}">
				<tr>
					<td><c:out value="${flight.flightNumber}"/></td>
					<td><c:out value="${flight.origin}"/></td>
					<td><c:out value="${flight.destination}"/></td>
					<td><c:out value="${departureMap[flight.id]}"/></td>
					<td><c:out value="${flight.priceEconomy}"/></td>
					<td><c:out value="${flight.priceBusiness}"/></td>
					<td>
						<c:choose>
							<c:when test="${not empty sessionScope.userId}">
								<a href="<c:url value='/user/book?flightId=${flight.id}'/>">Book</a>
							</c:when>
							<c:otherwise>
								<a href="<c:url value='/login'/>">Book</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>

<!-- move JS to bottom, wrap in DOMContentLoaded for safety -->
<script>
	document.addEventListener('DOMContentLoaded', function () {
		const form = document.getElementById('flightSearchForm');
		if (!form) return; // safety: if fragment missing, do nothing

		form.addEventListener('submit', function (e) {
			e.preventDefault();
			const params = new URLSearchParams(new FormData(form));
			params.set('ajax', 'true');

			// ensure action is absolute relative to context
			const action = form.getAttribute('action') || '/flights';
			const url = action + '?' + params.toString();

			fetch(url, {
				method: 'GET',
				headers: {
					'X-Requested-With': 'XMLHttpRequest',
					'Accept': 'text/html'
				}
			})
					.then(response => {
						if (!response.ok) throw new Error('Network response was not ok');
						return response.text();
					})
					.then(html => {
						// replace result container with fragment HTML
						document.getElementById('searchResults').innerHTML = html;
					})
					.catch(err => {
						console.error('Search request failed', err);
					});
		});
	});
</script>
</body>
</html>