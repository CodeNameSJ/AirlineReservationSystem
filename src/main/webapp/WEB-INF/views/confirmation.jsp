<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Booking Confirmed</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>"/>
</head>
<body>
<header>
    <%@ include file="fragments/header.jsp" %>
</header>
<main>
    <h2>Booking Confirmation</h2>
    <p>Your booking (ID: ${booking.bookingId}) is confirmed.</p>
    <p>Seat Number: ${booking.seatNumber}</p>
    <p>Price Paid: ${booking.pricePaid}</p>
    <p>Booked At: ${booking.bookedAt}</p>
    <a href="<c:url value='/search'/>">Book Another Flight</a>
</main>
<footer>
    <%@ include file="fragments/footer.jsp" %>
</footer>
</body>
</html>