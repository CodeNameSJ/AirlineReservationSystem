<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="<c:url value='../css/navbar.css'/>">
<nav class="navbar">
	<!-- Logo -->
	<div class="nav-left">
		<a href="${pageContext.request.contextPath}/">
			<img src="<c:url value='../images/logo.png'/>" class="logo" alt="">
		</a>
		<c:choose>
			<c:when test="${not empty sessionScope.username}">
				Welcome, ${sessionScope.username}
			</c:when>
		</c:choose>
	</div>

	<!-- Center Links -->
	<div class="nav-center">
		<a class="hover-decoration" href="${pageContext.request.contextPath}/">Home</a>
		<a class="hover-decoration" href="${pageContext.request.contextPath}/flight-list">Flights</a>
	</div>

	<!-- Right Links -->
	<div class="nav-right">
		<c:choose>
			<c:when test="${not empty sessionScope.role and sessionScope.role eq 'ADMIN'}">
				<div class="dropdown">
					<a href="#" class="drop-btn">Admin Dashboard <span class="arrow">&#9662;</span></a>
					<div class="dropdown-content">
						<a href="${pageContext.request.contextPath}/admin/flights">Manage Flights</a>
						<a href="${pageContext.request.contextPath}/admin/users">Manage Users</a>
						<a href="${pageContext.request.contextPath}/admin/bookings">Manage Bookings</a>
					</div>
				</div>
				<span>|</span>
				<a href="${pageContext.request.contextPath}/logout">Logout</a>
			</c:when>
			<c:when test="${not empty sessionScope.userId}">

				<a href="${pageContext.request.contextPath}/user/home">My Bookings</a>

				<span>|</span>
				<a href="${pageContext.request.contextPath}/logout">Logout</a>
			</c:when>
			<c:otherwise>
				<a href="${pageContext.request.contextPath}/login">Login</a>


				<a href="${pageContext.request.contextPath}/register" class="signup"> Sign up
					<div class="arrow-wrapper">
						<div class="arrow"></div>
					</div>
				</a>
			</c:otherwise>
		</c:choose>
	</div>
</nav>

<script>
	document.addEventListener('DOMContentLoaded', function () {
		document.querySelectorAll('.drop-btn').forEach(function (btn) {
			btn.addEventListener('click', function (e) {
				e.preventDefault();
				const dropdown = this.parentElement;
				dropdown.classList.toggle('show');

				// Close others
				document.querySelectorAll('.dropdown').forEach(function (dd) {
					if (dd !== dropdown) {
						dd.classList.remove('show');
					}
				});
			});
		});

		// Close on outside click
		document.addEventListener('click', function (e) {
			if (!e.target.closest('.dropdown')) {
				document.querySelectorAll('.dropdown').forEach(function (dd) {
					dd.classList.remove('show');
				});
			}
		});
	});
</script>