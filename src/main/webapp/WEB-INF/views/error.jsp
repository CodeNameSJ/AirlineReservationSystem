<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
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