<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Search Results</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>"/>
</head>
<body>
<header>
    <%@ include file="/WEB-INF/views/fragments/header.jsp" %>
</header>
<main>
    <h2>Available Flights</h2>
    <c:if test="${empty results}">
        <p>No flights found for selected route/date.</p>
    </c:if>
    <c:if test="${not empty results}">
        <table>
            <thead>
            <tr>
                <th>Flight</th>
                <th>Departure</th>
                <th>Arrival</th>
                <th>Price</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="s" items="${results}">
                <tr>
                    <td>${s.origin} → ${s.destination}</td>
                    <td>${s.departure}</td>
                    <td>${s.arrival}</td>
                    <td>${s.basePrice}</td>
                    <td>
                        <form action="<c:url value='/booking/confirm'/>" method="post">
                            <input type="hidden" name="scheduleId" value="${s.scheduleId}"/>
                            <label>Seat #: <input type="number" name="seatNumber" min="1" required/></label>
                            <input type="hidden" name="userId" value="${sessionScope.loggedInUserId}"/>
                            <button type="submit">Book</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</main>
<footer>
    <%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
</footer>
</body>
</html>