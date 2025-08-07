<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}resources/static/css/style.css"/>
</head>
<body>
<script src="${pageContext.request.contextPath}resources/static/js/script.js"></script>

<h1>Register</h1>
<form action="${pageContext.request.contextPath}/register/>" method="post">
    Name: <label>
    <input name="name" value="${user.name}"/>
</label><br/>
    Email: <label>
    <input name="email" value="${user.email}"/>
</label><br/>
    Username: <label>
    <input name="username" value="${user.username}"/>
</label><br/>
    Password: <label>
    <input type="password" name="password"/>
</label><br/>
    <button type="submit">Register</button>
</form>
<c:if test="${errorMessage != null}"><p style="color:red">${errorMessage}</p></c:if>
<a href="${pageContext.request.contextPath}/loginUser/>">Back to Login</a>
</body>
</html>