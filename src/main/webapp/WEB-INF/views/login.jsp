<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Login</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>"/>
</head>
<body>
<header>
    <%@ include file="/WEB-INF/views/fragments/header.jsp" %>
</header>
<main>
    <h2>Login</h2>
    <form:form method="post" action="${pageContext.request.contextPath}/login" modelAttribute="loginDTO">
        <div class="form-group">
            <label for="username">Username:</label>
            <form:input path="username" id="username" required="true"/>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <form:password path="password" id="password" required="true"/>
        </div>
        <button type="submit">Login</button>
    </form:form>
</main>
<footer>
    <%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
</footer>
</body>
</html>