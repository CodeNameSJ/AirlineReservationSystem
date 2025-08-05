<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Flight Search</title>
    <link rel="stylesheet" href="<c:url value='../../resources/css/style.css'/>"/>
</head>
<body>
<header>
    <h1>Airline Reservation System</h1>
    <%@ include file="fragments/header.jsp" %>
</header>

<main>
    <h2>Search Flights</h2>

    <form:form modelAttribute="searchRequest" action="${pageContext.request.contextPath}/search/results" method="post">
        <div>
            <label for="originCode">Origin:</label>
            <form:select path="originCode" id="originCode">
                <form:option value="" label="-- Select Airport --"/>
                <c:forEach var="a" items="${airports}">
                    <form:option value="${a.code}" label="${a.city} (${a.code})"/>
                </c:forEach>
            </form:select>
        </div>

        <div>
            <label for="destinationCode">Destination:</label>
            <form:select path="destinationCode" id="destinationCode">
                <form:option value="" label="-- Select Airport --"/>
                <c:forEach var="a" items="${airports}">
                    <form:option value="${a.code}" label="${a.city} (${a.code})"/>
                </c:forEach>
            </form:select>
        </div>

        <div>
            <label for="date">Travel Date:</label>
            <input type="date" id="date" name="date" required="true"/>
        </div>

        <button type="submit">Search</button>
    </form:form>
</main>

<footer>
    <%@ include file="fragments/footer.jsp" %>
</footer>
</body>
</html>