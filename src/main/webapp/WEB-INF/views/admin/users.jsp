<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Manage Users</title>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>"/>
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
			<th>Username</th>
			<th>Email</th>
			<th>Role</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="user" items="${users}">
			<tr>
				<td>${user.username}</td>
				<td>${user.email}</td>
				<td>
					<a href="${pageContext.request.contextPath}/admin/users/delete/${user.id}">Delete</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>