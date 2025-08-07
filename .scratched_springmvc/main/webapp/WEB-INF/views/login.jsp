<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
<<<<<<< Updated upstream
  <meta charset="UTF-8" />
  <title>Login</title>
  <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" />
</head>
<body>
  <%@ include file="/WEB-INF/views/fragments/header.jsp" %>
  <main>
    <h2>Login</h2>
    <form:form method="post" action="${pageContext.request.contextPath}/login" modelAttribute="loginDTO">
      <div class="form-group">
        <label for="username">Username:</label>
        <form:input path="username" id="username" required="true" />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <form:password path="password" id="password" required="true" />
      </div>
      <button type="submit">Login</button>
    </form:form>
  </main>
  <%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
=======
    <meta charset="UTF-8"/>
    <title>Login</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>"/>
</head>
<body>
<header>
    <%@ include file="fragments/header.jsp" %>
</header>
<main>
    <h2>Login</h2>

    <c:if test="${param.error != null}">
        <p style="color:red;">Invalid username or password</p>
    </c:if>

    <c:if test="${param.logout != null}">
        <p style="color:green;">You have been logged out</p>
    </c:if>

    <c:if test="${param.registerSuccess != null}">
        <p style="color:green;">Registration successful. Please log in.</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <label for="username">Username:</label>
        <label>
            <input type="text" name="username" required/>
        </label><br/>

        <label for="password">Password:</label>
        <label>
            <input type="password" name="password" required/>
        </label><br/>

        <button type="submit">Login</button>
    </form>
</main>
<footer>
    <%@ include file="fragments/footer.jsp" %>
</footer>
>>>>>>> Stashed changes
</body>
</html>