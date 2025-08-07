<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Manage Schedules</title>
    <link rel="stylesheet" href="<c:url value='../../../resources/css/style.css'/>"/>
</head>
<body>
<%@ include file="../fragments/header.jsp" %>
<main>
    <h2>All Schedules</h2>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Flight ID</th>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="s" items="${schedules}">
            <tr>
                <td>${s.id}</td>
                <td>${s.flight.id}</td>
                <td>${s.departure}</td>
                <td>${s.arrival}</td>
                <td>${s.basePrice}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <h3>Add Schedule</h3>
    <form action="<c:url value='/admin/schedules/add'/>" method="post">
        <label>Flight ID: <input type="number" name="flight.id" required/></label>
        <label>Departure: <input type="datetime-local" name="departure" required/></label>
        <label>Arrival: <input type="datetime-local" name="arrival" required/></label>
        <label>Base Price: <input type="number" step="0.01" name="basePrice" required/></label>
        <button type="submit">Add</button>
    </form>
</main>
<%@ include file="../fragments/footer.jsp" %>
</body>
</html>