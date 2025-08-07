<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Bookings</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>"/>
    <style>
        table { width: 100%; border-collapse: collapse; margin-top: 1em; }
        th, td { padding: 0.75em; border: 1px solid #ccc; }
        form { display: inline; }
        .cancel-btn { background: red; color: white; border: none; padding: 0.4em 0.7em; cursor: pointer; }
    </style>
</head>
<body>
<%@ include file="fragments/header.jsp" %>

<h2>Your Bookings</h2>

<c:if test="${empty bookings}">
    <p>You have no bookings.</p>
</c:if>

<c:if test="${not empty bookings}">
    <table>
        <thead>
            <tr>
                <th>Flight</th>
                <th>Seats</th>
                <th>Booked At</th>
                <th>Price</th>
                <th>Total</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${bookings}">
            <tr>
                <td>${b.flight.origin} → ${b.flight.destination}
                    <br/>${b.flight.departureDate} @ ${b.flight.departureTime}
                </td>
                <td>${b.seatsBooked}</td>
                <td>${b.bookingTime}</td>
                <td>&#8377; ${b.flight.price}</td>
                <td>&#8377; ${b.seatsBooked * b.flight.price}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/dashboard/cancel/${b.id}">
                        <button class="cancel-btn" type="submit">Cancel</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<h3>Total Paid: &#8377; ${totalPaid}</h3>

<%@ include file="fragments/footer.jsp" %>
</body>
</html>