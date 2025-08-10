<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Login</title>
<%--	<link rel="stylesheet" href="<c:url value='./css/style.css'/>">--%>
<%--	<link rel="stylesheet" href="${pageContext.request.contextPath}resources/static/css/style.css"/>--%>

</head>
<body>
<h2>Login</h2>

<c:if test="${param.error != null}">
	<p style="color:red;">Invalid username or password</p>
</c:if>
<c:if test="${param.logout != null}">
	<p style="color:green;">You have been logged out</p>
</c:if>

<form action="${pageContext.request.contextPath}/perform_login" method="post">
	<label>Username: <input type="text" name="username"></label><br/>
	<label>Password: <input type="password" name="password"></label><br/>
	<input type="submit" value="Login">
</form>
</body>
</html>
