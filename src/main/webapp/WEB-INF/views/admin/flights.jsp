<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Manage Flights</title></head>
<body>
<h1>Manage Flights</h1>
<a href="<c:url value='/admin/flights/new'/>">Add New Flight</a>
<table border="1">
    <tr><th>Flight No</th><th>Origin</th><th>Destination</th><th>Actions</th></tr>
    <c:forEach var="f" items="${flights}">
        <tr>
            <td>${f.flightNumber}</td>
            <td>${f.origin}</td>
            <td>${f.destination}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/flights/edit?id=${f.id}/>">Edit</a>
                <form action="${pageContext.request.contextPath}/admin/flights/delete/>" style="display:inline;" method="post">
                    <input type="hidden" name="id" value="${f.id}"/>
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>