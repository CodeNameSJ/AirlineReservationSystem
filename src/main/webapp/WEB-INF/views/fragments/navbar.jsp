<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
		<a class="hover-link" style="font-size: 1.36rem" href="${pageContext.request.contextPath}/">Home</a>
		<a class="hover-link" style="font-size: 1.36rem" href="${pageContext.request.contextPath}/flight-list">Flights</a>
	</div>

	<!-- Right Links -->
	<div class="hover-link nav-right">
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
				<span>/</span>
				<a href="${pageContext.request.contextPath}/register">Sign Up</a>
			</c:otherwise>
		</c:choose>
	</div>
</nav>

<!-- CSS -->
<style>
	.navbar {
		display: flex;
		justify-content: space-between;
		align-items: center;
		background: #6B6ECC linear-gradient(45deg, #04051dea 0%, #2b566e 100%);
		padding: 5px 10px;
		border-radius: 20px;
		color: #fff;
		margin-bottom: 20px;
		box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
	}

	.navbar a {
		color: white;
		text-decoration: none;
		margin: 8px 8px;
	}

	.navbar a:hover {
		text-decoration: none;
	}

	.hover-link {
		font-size: 1.2rem;
		color: #fff;
		position: relative;
		text-decoration: none;
		padding-bottom: 3px;
		transition: color 0.3s ease;
	}

	.hover-link::after {
		content: '';
		position: absolute;
		left: 0;
		bottom: 0;
		width: 0;
		height: 2px;
		background-color: currentColor;
		transition: width 0.3s ease;
	}

	.hover-link:hover {
		color: #80dcff;
	}

	.hover-link:hover::after {
		width: 100%;
	}

	.logo {
		height: 40px;
	}

	.nav-left, .nav-center, .nav-right {
		display: flex;
		align-items: center;
	}

	/* Dropdown Styles */
	.dropdown {
		position: relative;
		display: inline-block;
	}

	.drop-btn {
		cursor: pointer;
	}

	.dropdown-content {
		display: none;
		position: absolute;
		background-color: #103b6e;
		min-width: 160px;
		box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2);
		z-index: 1;
		border-radius: 15px;
	}

	.dropdown-content a {
		color: white;
		padding: 10px 16px;
		text-decoration: none;
		display: block;
	}

	.dropdown-content a:hover {
		background-color: #004ba9;
		border-radius: 15px;
	}

	.dropdown.show .dropdown-content {
		display: block;
	}
</style>

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