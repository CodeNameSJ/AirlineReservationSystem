<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/fragments/navbar.jsp" %>

<h2>Manage Bookings</h2>
<table border="1">
    <thead>
        <tr>
            <th>Booking ID</th>
            <th>Flight</th>
            <th>User</th>
            <th>Date</th>
            <th>Seats</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="booking" items="${bookings}">
        <tr>
            <td>${booking.id}</td>
            <td>${booking.flight.flightNumber}</td>
            <td>${booking.user.username}</td>
            <td>${booking.bookingDate}</td>
            <td>${booking.seats}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
