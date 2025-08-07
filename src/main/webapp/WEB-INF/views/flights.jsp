<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Manage Flights</title>
  <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>" />
</head>
<body>
  <%@ include file="fragments/header.jsp" %>
  <main>
    <h2>All Flights</h2>
    <table>
      <thead><tr><th>ID</th><th>Origin</th><th>Destination</th><th>Aircraft</th></tr></thead>
      <tbody>
        <c:forEach var="f" items="${flights}">
          <tr>
            <td>${f.id}</td>
            <td>${f.origin.code}</td>
            <td>${f.destination.code}</td>
            <td>${f.aircraftType}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <h3>Add Flight</h3>
    <form action="<c:url value='/admin/flights/add'/>" method="post">
      <label>Origin ID: <input type="number" name="origin.id" required /></label>
      <label>Destination ID: <input type="number" name="destination.id" required /></label>
      <label>Aircraft Type: <input type="text" name="aircraftType" required /></label>
      <button type="submit">Add</button>
    </form>
  </main>
  <%@ include file="fragments/footer.jsp" %>
=======
=======
>>>>>>> Stashed changes
<html>
<head>
    <title>Search Flights</title>
    <link rel="stylesheet" href="<c:url value='./css/style.css'/>">
</head>
<body>
<h1>Search Flights</h1>

<form action="<c:url value='/flights'/>" method="get">
    Origin: <input type="text" name="origin" value="${param.origin}" />
    Destination: <input type="text" name="destination" value="${param.destination}" />
    Date: <input type="date" name="date" value="${param.date}" />
    <button type="submit">Search</button>
</form>

<h2>Results</h2>
<table border="1">
    <tr><th>Flight No</th><th>Origin</th><th>Destination</th><th>Departure</th><th>Price (Economy)</th><th>Price (Business)</th><th>Action</th></tr>
    <c:forEach var="flight" items="${flights}">
        <tr>
            <td>${flight.flightNumber}</td>
            <td>${flight.origin}</td>
            <td>${flight.destination}</td>
            <td>${flight.departureTime}</td>
            <td>${flight.priceEconomy}</td>
            <td>${flight.priceBusiness}</td>
            <td>
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal != null}">
                        <a href="<c:url value='/user/book?flightId=${flight.id}'/>">Book</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='./loginUser'/>">Login to Book</a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
</body>
</html>