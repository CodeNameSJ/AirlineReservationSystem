<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Register</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>"/>
    <style>
      .error { color: red; font-size: 0.9em; }
      .success { color: green; }
      .form-group { margin-bottom: 1em; }
      label { display: block; margin-bottom: 0.3em; }
      input { width: 100%; padding: 0.5em; box-sizing: border-box; }
    </style>
</head>
<body>
<header>
    <%@ include file="fragments/header.jsp" %>
</header>
<main>
    <h2>Register</h2>

    <!-- Success message -->
    <c:if test="${not empty message}">
        <div class="success">${message}</div>
    </c:if>

    <!-- Global registration error -->
    <c:if test="${not empty registrationError}">
        <div class="error">${registrationError}</div>
    </c:if>

    <form:form method="post"
               action="${pageContext.request.contextPath}/register"
               modelAttribute="user">

        <div class="form-group">
            <label for="username">Username:</label>
            <form:input path="username" id="username" required="true" />
            <form:errors path="username" cssClass="error" />
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <form:input  path="email"
                         id="email"
                         type="email"
                         required="true" />
            <form:errors path="email" cssClass="error" />
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <form:password path="password"
                           id="password"
                           required="true" />
            <form:errors path="password" cssClass="error" />
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm Password:</label>
            <form:password path="confirmPassword"
                           id="confirmPassword"
                           required="true" />
            <form:errors path="confirmPassword" cssClass="error" />
        </div>

        <button type="submit">Register</button>
    </form:form>
</main>
<footer>
    <%@ include file="fragments/footer.jsp" %>
</footer>
</body>
</html>