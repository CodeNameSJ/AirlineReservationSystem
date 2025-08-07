<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Admin Login</title></head>
<body>
<h1>Admin Login</h1>
<form action="${pageContext.request.contextPath}/admin_login/>" method="post">
    Username: <label>
    <input name="username"/>
</label><br/>
    Password: <label>
    <input type="password" name="password"/>
</label><br/>
    <button type="submit">Login</button>
</form>
<c:if test="${param.error != null}"><p style="color:red">Invalid credentials</p></c:if>
</body>
</html>