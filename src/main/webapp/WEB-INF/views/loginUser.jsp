<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>User Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
</head>
<body>
<h1>User Login</h1>

<form action="${pageContext.request.contextPath}/perform_login" method="post">
	<label>Username: <input name="username" required /></label><br/>
	<label>Password: <input type="password" name="password" required /></label><br/>

	<c:if test="${not empty _csrf}">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</c:if>

	<button type="submit">Login</button>
</form>

<a href="${pageContext.request.contextPath}/register">Register</a>

<c:if test="${param.error != null}">
	<p style="color:red">Invalid credentials</p>
</c:if>

</body>
</html>