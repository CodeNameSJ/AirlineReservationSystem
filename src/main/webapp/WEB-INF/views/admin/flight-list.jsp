<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/fragments/navbar.jsp" %>

<h2>Manage Flights</h2>
<a href="${pageContext.request.contextPath}/admin/flights/add">Add New Flight</a>
<table border="1">
    <thead>
        <tr>
            <th>Flight No</th>
            <th>Airline</th>
            <th>From</th>
            <th>To</th>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="flight" items="${flights}">
        <tr>
            <td>${flight.flightNumber}</td>
            <td>${flight.airline}</td>
            <td>${flight.source}</td>
            <td>${flight.destination}</td>
            <td>${flight.departureTime}</td>
            <td>${flight.arrivalTime}</td>
            <td>${flight.price}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/flights/edit/${flight.id}">Edit</a>
                |
                <a href="${pageContext.request.contextPath}/admin/flights/delete/${flight.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>