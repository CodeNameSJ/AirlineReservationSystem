<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Airline Reservation - Home</title>
	<link rel="stylesheet" href="<c:url value='./css/style.css'/>">
	<link rel="stylesheet" href="<c:url value='./css/card.css'/>">
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

	<h1>Welcome</h1>

	<h1>Search Flights</h1>

	<form action="${pageContext.request.contextPath}/flights" method="get">
		Origin: <label>
		<input type="text" name="origin" value="${param.origin}"/>
	</label>
		Destination: <label>
		<input type="text" name="destination" value="${param.destination}"/>
	</label>
		Date: <label>
		<input type="date" name="date" value="${param.date}"/>
	</label>
		<button type="submit">Search</button>
	</form>


	<hr/>
	<h2>Featured Flights</h2>
	<c:if test="${not empty flights}">
		<div class="card-container">
			<c:forEach var="f" items="${flights}">
				<div class="card">
					<div class="content">
						<div class="title">${f.origin} ➡ ${f.destination}</div>
						<div class="price">Economy: $${f.priceEconomy}</div>
						<div class="price">Business: $${f.priceBusiness}</div>
						<div class="description">
							Departure Time: ${f.departureTime}
						</div>
					</div>
					<a href="${pageContext.request.contextPath}/user/book?flightId=${f.id}">
						<button class="btn">Book Now</button>
					</a>
				</div>
			</c:forEach>
		</div>
	</c:if>

</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>