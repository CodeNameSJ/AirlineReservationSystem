<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>User Registration</title></head>
<body>
<h1>Register</h1>
<form action="<c:url value='./register'/>" method="post">
	Name: <input name="name" value="${user.name}"/><br/>
	Email: <input name="email" value="${user.email}"/><br/>
	Username: <input name="username" value="${user.username}"/><br/>
	Password: <input type="password" name="password"/><br/>
	<button type="submit">Register</button>
</form>
<c:if test="${errorMessage != null}"><p style="color:red">${errorMessage}</p></c:if>
<a href="<c:url value='./loginUser'/>">Back to Login</a>
</body>
</html>