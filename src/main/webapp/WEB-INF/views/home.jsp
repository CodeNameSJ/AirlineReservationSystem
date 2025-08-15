<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Airline Reservation - Home</title>
	<link rel="stylesheet" href="<c:url value='./css/style.css'/>">
	<link rel="stylesheet" href="<c:url value='./css/card.css'/>">
	<link rel="stylesheet" href="<c:url value='../css/search.css'/>">
	<style>
		.button {
			padding: 1.3em 3em;
			font-size: 12px;
			text-transform: uppercase;
			letter-spacing: 3px;
			font-weight: 500;
			color: #000;
			background-color: #fff;
			border: none;
			border-radius: 45px;
			box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
			transition: all 0.3s ease 0s;
			cursor: pointer;
			outline: none;
		}

		.button:hover {
			background-color: #23c483;
			box-shadow: 0 15px 20px rgba(46, 229, 157, 0.4);
			color: #fff;
			transform: translateY(-7px);
		}

		.button:active {
			transform: translateY(-1px);
		}
	</style>
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

	<jsp:include page="/WEB-INF/views/fragments/searchForm.jsp"/>

	<hr/>
	<h2>Featured Flights</h2>
	<c:if test="${not empty flights}">
		<div class="card-container">
			<c:forEach var="f" items="${flights}">
				<div class="card">
					<div class="content">
						<div class="title">${f.origin} <span class="form-separator">&#8594;</span> ${f.destination}</div>
						<div class="price">Economy: $${f.priceEconomy}</div>
						<div class="price">Business: $${f.priceBusiness}</div>
						<div class="description">
							Departure Time: ${departureMap}
						</div>
					</div>
					<a href="${pageContext.request.contextPath}/user/book?flightId=${f.id}">
						<button class="button">Book Now</button>
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