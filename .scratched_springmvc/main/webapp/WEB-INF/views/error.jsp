<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<<<<<<< Updated upstream
  <meta charset="UTF-8" />
  <title>Error</title>
  <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" />
</head>
<body>
<%@ include file="fragments/header.jsp" %>
<main>
  <h2>An error occurred</h2>
  <p>${message}</p>
  <a href="<c:url value='/'/>">Home</a>
</main>
<%@ include file="fragments/footer.jsp" %>
</body>
</html>
=======
    <meta charset="UTF-8"/>
    <title>Error ${errorCode}</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>"/>
    <style>
        .error-container { text-align: center; margin: 5em auto; }
        .error-code { font-size: 4em; margin-bottom: 0.2em; }
        .error-message { font-size: 1.5em; color: #b00; }
        a { display: inline-block; margin-top: 1em; }
    </style>
</head>
<body>
<%@ include file="fragments/header.jsp" %>
<div class="error-container">
    <div class="error-code">${errorCode}</div>
    <div class="error-message">${errorMessage}</div>
    <a href="<c:url value='/'/>">Return to Home</a>
</div>
<%@ include file="fragments/footer.jsp" %>
</body>
</html>
>>>>>>> Stashed changes
