<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Login</title>
	<link rel="stylesheet" href="<c:url value='./css/form.css'/>">
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>

	<div class="login-box">
		<p>Login</p>
		<form action="${pageContext.request.contextPath}/login" method="post">
			<div class="user-box">
				<input required name="username" id="username" type="text" autocomplete="on">
				<label for="username">Username</label>
			</div>
			<div class="user-box">
				<input required name="password" id="password" type="password">
				<label for="password">Password</label>
			</div>
			<button type="submit" class="animated-button">
				<span></span><span></span><span></span><span></span>
				Login
			</button>
		</form>
		<p>Don't have an account?
			<a href="${pageContext.request.contextPath}/register" class="a2">Signup</a>
		</p>
		<c:if test="${not empty error}">
			<p style="color:red">${error}</p>
		</c:if>
	</div>

</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>