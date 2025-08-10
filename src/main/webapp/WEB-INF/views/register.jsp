<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>User Registration</title>
	<link rel="stylesheet" href="<c:url value='./css/styles.css'/>">
	<style>
		.input {
			max-width: 290px;
			height: 44px;
			background-color: #05060f0a;
			border-radius: .5rem;
			padding: 0 1rem;
			border: 2px solid transparent;
			font-size: 1rem;
			transition: border-color .3s cubic-bezier(.25, .01, .25, 1) 0s, color .3s cubic-bezier(.25, .01, .25, 1) 0s, background .2s cubic-bezier(.25, .01, .25, 1) 0s;
		}

		.label {
			display: block;
			margin-bottom: .3rem;
			font-size: .9rem;
			font-weight: bold;
			color: #05060f99;
			transition: color .3s cubic-bezier(.25, .01, .25, 1) 0s;
		}

		.input:hover, .input:focus, .input-group:hover .input {
			outline: none;
			border-color: #05060f;
		}

		.input-group:hover .label, .input:focus {
			color: #05060fc2;
		}
	</style>
</head>
<body>
<header>
	<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
</header>
<main>
	<script src="<c:url value='./js/script.js'/>"></script>
	<h2>Register</h2>
	<form action="${pageContext.request.contextPath}/register" method="post">
		<div class="input-group">
			<label class="label">Username</label>
			<label for="reg-username"></label>
			<input autocomplete="off" name="username" id="reg-username" class="input" type="text" required>
			<br>

			<label class="label">Email</label>
			<label for="email"></label>
			<input autocomplete="off" name="email" id="email" class="input" type="email" required>
			<br>

			<label class="label">Password</label>
			<label for="reg-password"></label>
			<input name="password" id="reg-password" class="input" type="password" required>
			<div></div>
		</div>

		<button type="submit">Create Account</button>
		<span> | </span>
		<button type="button" onclick="window.location.href='${pageContext.request.contextPath}/login'">Back to Login
		</button>
	</form>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>