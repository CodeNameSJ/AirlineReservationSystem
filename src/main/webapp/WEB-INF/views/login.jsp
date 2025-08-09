<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>User Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}resources/static/css/style.css"/>
</head>
<body>
<script src="${pageContext.request.contextPath}resources/static/js/script.js"></script>
<h1>User Login</h1>
<form action="${pageContext.request.contextPath}/user_login/>" method="post">
	Username: <label>
	<input name="username"/>
</label><br/>
	Password: <label>
	<input type="password" name="password"/>
</label><br/>
	<button type="submit">Login</button>
</form>
<a href="${pageContext.request.contextPath}/register/>">Register</a>
<c:if test="${param.error != null}"><p style="color:red">Invalid credentials</p></c:if>
</body>
</html>