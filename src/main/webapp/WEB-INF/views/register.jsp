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

		/*new*/

		/* From Uiverse.io by glisovic01 */
		.login-box {
			position: absolute;
			top: 50%;
			left: 50%;
			width: 400px;
			padding: 40px;
			margin: 20px auto;
			transform: translate(-50%, -55%);
			background: rgba(0, 0, 0, .9);
			box-sizing: border-box;
			box-shadow: 0 15px 25px rgba(0, 0, 0, .6);
			border-radius: 10px;
		}

		.login-box p:first-child {
			margin: 0 0 30px;
			padding: 0;
			color: #fff;
			text-align: center;
			font-size: 1.5rem;
			font-weight: bold;
			letter-spacing: 1px;
		}

		.login-box .user-box {
			position: relative;
		}

		.login-box .user-box input {
			width: 100%;
			padding: 10px 0;
			font-size: 16px;
			color: #fff;
			margin-bottom: 30px;
			border: none;
			border-bottom: 1px solid #fff;
			outline: none;
			background: transparent;
		}

		.login-box .user-box label {
			position: absolute;
			top: 0;
			left: 0;
			padding: 10px 0;
			font-size: 16px;
			color: #fff;
			pointer-events: none;
			transition: .5s;
		}

		.login-box .user-box input:focus ~ label,
		.login-box .user-box input:valid ~ label {
			top: -20px;
			left: 0;
			color: #fff;
			font-size: 12px;
		}

		.login-box form a {
			position: relative;
			display: inline-block;
			padding: 10px 20px;
			font-weight: bold;
			color: #fff;
			font-size: 16px;
			text-decoration: none;
			text-transform: uppercase;
			overflow: hidden;
			transition: .5s;
			margin-top: 40px;
			letter-spacing: 3px
		}

		.login-box a:hover {
			background: #fff;
			color: #272727;
			border-radius: 5px;
		}

		.login-box a span {
			position: absolute;
			display: block;
		}

		.login-box a span:nth-child(1) {
			top: 0;
			left: -100%;
			width: 100%;
			height: 2px;
			background: linear-gradient(90deg, transparent, #fff);
			animation: btn-anim1 1.5s linear infinite;
		}

		@keyframes btn-anim1 {
			0% {
				left: -100%;
			}

			50%, 100% {
				left: 100%;
			}
		}

		.login-box a span:nth-child(2) {
			top: -100%;
			right: 0;
			width: 2px;
			height: 100%;
			background: linear-gradient(180deg, transparent, #fff);
			animation: btn-anim2 1.5s linear infinite;
			animation-delay: .375s
		}

		@keyframes btn-anim2 {
			0% {
				top: -100%;
			}

			50%, 100% {
				top: 100%;
			}
		}

		.login-box a span:nth-child(3) {
			bottom: 0;
			right: -100%;
			width: 100%;
			height: 2px;
			background: linear-gradient(270deg, transparent, #fff);
			animation: btn-anim3 1.5s linear infinite;
			animation-delay: .75s
		}

		@keyframes btn-anim3 {
			0% {
				right: -100%;
			}

			50%, 100% {
				right: 100%;
			}
		}

		.login-box a span:nth-child(4) {
			bottom: -100%;
			left: 0;
			width: 2px;
			height: 100%;
			background: linear-gradient(360deg, transparent, #fff);
			animation: btn-anim4 1.5s linear infinite;
			animation-delay: 1.125s
		}

		@keyframes btn-anim4 {
			0% {
				bottom: -100%;
			}

			50%, 100% {
				bottom: 100%;
			}
		}

		.login-box p:last-child {
			color: #aaa;
			font-size: 14px;
		}

		.login-box a.a2 {
			color: #fff;
			text-decoration: none;
		}

		.login-box a.a2:hover {
			background: transparent;
			color: #aaa;
			border-radius: 5px;
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


<%--	<div class="login-box">--%>
<%--		<p>Signup</p>--%>
<%--		<form action="${pageContext.request.contextPath}/register" method="post">--%>
<%--			<div class="user-box">--%>
<%--				<input required name="username" type="text">--%>
<%--				<label>Username</label>--%>
<%--			</div>--%>
<%--			<div class="user-box">--%>
<%--				<input required name="email" type="email">--%>
<%--				<label>Email</label>--%>
<%--			</div>--%>
<%--			<div class="user-box">--%>
<%--				<input required name="email" type="password">--%>
<%--				<label>Password</label>--%>
<%--			</div>--%>
<%--			<a>--%>
<%--				<span></span>--%>
<%--				<span></span>--%>
<%--				<span></span>--%>
<%--				<span></span>--%>
<%--				Submit--%>
<%--			</a>--%>
<%--		</form>--%>
<%--		<p>Already have an account? <a href="" class="a2">Login!</a></p>--%>
<%--	</div>--%>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>