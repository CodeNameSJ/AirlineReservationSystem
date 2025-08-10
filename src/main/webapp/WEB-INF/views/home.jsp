<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Airline Reservation - Home</title>
	<link rel="stylesheet" href="<c:url value='./css/style.css'/>">
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
		<ul>
			<c:forEach var="f" items="${flights}">
				<li>
					<a href="${pageContext.request.contextPath}/flight/${f.id}">${f.origin} → ${f.destination}</a>
					&nbsp;|&nbsp; ${f.departureTime} &nbsp;|&nbsp; ${f.price} | <a
						href="${pageContext.request.contextPath}/user/book?flightId=${f.id}">Book</a>
				</li>
			</c:forEach>
		</ul>
	</c:if>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>