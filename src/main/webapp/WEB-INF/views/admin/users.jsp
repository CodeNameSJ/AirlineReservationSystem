<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Manage Users</title>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>"/>
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

	<h2>Registered Users</h2>

	<table border="1" cellpadding="5">
		<thead>
		<tr>
			<th>ID</th>
			<th>Username</th>
			<th>Email</th>
			<th>Actions</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="user" items="${users}">
			<tr>
				<td><c:out value="${user.id}"/></td>
				<td><c:out value="${user.username}"/></td>
				<td><c:out value="${user.email}"/></td>
				<td>
					<form method="get" action="${pageContext.request.contextPath}/admin/users/edit"
					      style="display:inline;">
						<input type="hidden" name="id" value="${user.id}"/>
						<button type="submit">Edit</button>
						<button type="button" onclick="toggleWarning(${user.id}, true)">
							Delete
						</button>
					</form>
				</td>
			</tr>

			<tr id="warning-${user.id}" class="warning-row" style="display:none;">
				<td colspan="5">
					<strong>&#9888;
						Are you sure you want to delete this user? Deleting it will also remove all related bookings.
					</strong>
					<br/>
					<form method="post" action="${pageContext.request.contextPath}/admin/users/delete"
					      style="display:inline;">
						<input type="hidden" name="id" value="${user.id}"/>
						<input type="hidden" name="confirm" value="true"/>
						<button type="submit" style="background-color:red; color:white;">
							Yes, Delete
						</button>
					</form>
					<button type="button" onclick="toggleWarning(${user.id}, false)">Cancel</button>
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