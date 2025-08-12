<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>User Registration</title>
	<link rel="stylesheet" href="<c:url value='./css/form.css'/>">
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>
	<script src="<c:url value='./js/script.js'/>"></script>

	<div class="login-box">
		<p>Register</p>
		<form action="${pageContext.request.contextPath}/register" method="post">
			<div class="user-box">
				<input required name="username" id="username" type="text"">
				<label for="username">Username</label>
			</div>
			<div class="user-box">
				<input required name="email" id="email" type="email" autocomplete="on">
				<label for="email">Email</label>
			</div>
			<div class="user-box">
				<input required name="password" id="password" type="password">
				<label for="password">Password</label>
			</div>
			<button type="submit" class="animated-button">
				<span></span><span></span><span></span><span></span>
				Register
			</button>
		</form>
		<span><br></span>
		<p>Already have an account?
			<a href="${pageContext.request.contextPath}/login" class="a2">Login</a>
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