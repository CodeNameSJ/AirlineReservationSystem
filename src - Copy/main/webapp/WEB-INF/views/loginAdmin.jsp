<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Admin Login</title></head>
<body>
<h1>Admin Login</h1>
<form action="<c:url value='./loginAdmin-post'/>" method="post">
    Username: <input name="username"/><br/>
    Password: <input type="password" name="password"/><br/>
    <button type="submit">Login</button>
</form>
<c:if test="${param.error != null}"><p style="color:red">Invalid credentials</p></c:if>
</body>
</html>