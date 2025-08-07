<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Register</title>
  <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" />
</head>
<body>
  <%@ include file="fragments/header.jsp" %>
  <main>
    <h2>Register</h2>
    <form:form method="post" action="${pageContext.request.contextPath}/register" modelAttribute="user">
      <div class="form-group">
        <label for="username">Username:</label>
        <form:input path="username" id="username" required="true" />
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <form:input path="email" id="email" type="email" required="true" />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <form:password path="password" id="password" required="true" />
      </div>
      <button type="submit">Register</button>
    </form:form>
  </main>
  <%@ include file="fragments/footer.jsp" %>
=======
<html>
<head><title>User Registration</title></head>
<body>
=======
<html>
<head><title>User Registration</title></head>
<body>
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
</body>
</html>