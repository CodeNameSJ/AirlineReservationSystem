<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Flights</title>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}resources/css/style.css'/>">--%>
</head>
<body>
<h1>Search Flights</h1>

<form action="${pageContext.request.contextPath}/flights" method="get">
    Origin: <label>
    <input type="text" name="origin" value="${param.origin}"/>
</label>
    Destination: <label>
    <input type="text" name="destination" value="${param.destination}"/>
</label>
    Date: <label>
    <input type="date" name="date" value="${param.date}"/>
</label>
    <button type="submit">Search</button>
</form>

<h2>Results</h2>
<table style="border:1px solid black">
    <tr>
        <th>Flight No</th>
        <th>Origin</th>
        <th>Destination</th>
        <th>Departure</th>
        <th>Price (Economy)</th>
        <th>Price (Business)</th>
        <th>Action</th>
    </tr>
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
                        <a href="${pageContext.request.contextPath}/user/book?flightId=${flight.id}">Book</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login">Login to Book</a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>