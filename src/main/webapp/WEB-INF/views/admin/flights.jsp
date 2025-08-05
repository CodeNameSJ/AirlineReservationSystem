<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Manage Flights</title>
    <link rel="stylesheet" href="<c:url value='../../../resources/css/style.css'/>"/>
</head>
<body>
<header>
    <%@ include file="../fragments/header.jsp" %>
</header>
<main>
    <h2>All Flights</h2>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Origin</th>
            <th>Destination</th>
            <th>Aircraft</th>
        </tr>
        </thead>
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
        <label>Origin ID: <input type="number" name="origin.id" required/></label>
        <label>Destination ID: <input type="number" name="destination.id" required/></label>
        <label>Aircraft Type: <input type="text" name="aircraftType" required/></label>
        <button type="submit">Add</button>
    </form>
</main>
<footer>
    <%@ include file="../fragments/footer.jsp" %>
</footer>
</body>
</html>