<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>User Login</title></head>
<body>
<h1>User Login</h1>
<form action="<c:url value='./loginUser-post'/>" method="post">
    Username: <input name="username"/><br/>
    Password: <input type="password" name="password"/><br/>
    <button type="submit">Login</button>
</form>
<a href="<c:url value='./register'/>">Register</a>
<c:if test="${param.error != null}"><p style="color:red">Invalid credentials</p></c:if>
</body>
</html>