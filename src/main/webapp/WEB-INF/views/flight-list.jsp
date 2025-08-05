<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Flight Search Results</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>"/>
    <style>
        .filter-form {
            margin-bottom: 1.5em;
        }

        .filter-form input {
            padding: 0.5em;
            margin-right: 0.5em;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 1em;
        }

        th, td {
            padding: 0.75em;
            border: 1px solid #ccc;
            text-align: left;
        }

        .pagination a {
            margin: 0 0.5em;
            text-decoration: none;
        }

        .pagination {
            font-size: 0.9em;
        }
    </style>
</head>
<body>
<header>
    <%@ include file="fragments/header.jsp" %>
</header>

<main>
    <h2>Search Flights</h2>

    <!-- Filter form -->
    <form class="filter-form" action="${pageContext.request.contextPath}/flights" method="get">
        <label>
            <input name="origin" placeholder="Origin" value="${origin}"/>
        </label>
        <label>
            <input name="destination" placeholder="Destination" value="${destination}"/>
        </label>
        <label>
            <input name="date" type="date" value="${date}"/>
        </label>
        <button type="submit">Search</button>
    </form>

    <!-- Results table -->
    <c:choose>
        <c:when test="${not empty flights}">
            <table>
                <thead>
                <tr>
                    <th>Origin</th>
                    <th>Destination</th>
                    <th>Departure</th>
                    <th>Arrival</th>
                    <th>Price</th>
                    <th>Capacity</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="f" items="${flights}">
                    <tr>
                        <td><c:out value="${f.origin}"/></td>
                        <td><c:out value="${f.destination}"/></td>
                        <td>
                            <c:out value="${f.departureDate}"/>
                            &bull;
                            <c:out value="${f.departureTime}"/>
                        </td>
                        <td>
                            <c:out value="${f.arrivalDate}"/>
                            &bull;
                            <c:out value="${f.arrivalTime}"/>
                        </td>
                        <td>&#8377; <c:out value="${f.price}"/></td>
                        <td><c:out value="${f.capacity}"/></td>
                        <td>
                            <a href="<c:url value='/booking?flightId=${f.id}'/>">Book</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No flights found matching your criteria.</p>
        </c:otherwise>
    </c:choose>

    <!-- Pagination -->
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="<c:url value='/flights'>
                          <c:param name='origin' value='${origin}'/>
                          <c:param name='destination' value='${destination}'/>
                          <c:param name='date' value='${date}'/>
                          <c:param name='page' value='${currentPage - 1}'/>
                      </c:url>">&laquo; Prev</a>
        </c:if>

        <span>Page ${currentPage} of ${totalPages}</span>

        <c:if test="${currentPage < totalPages}">
            <a href="<c:url value='/flights'>
                          <c:param name='origin' value='${origin}'/>
                          <c:param name='destination' value='${destination}'/>
                          <c:param name='date' value='${date}'/>
                          <c:param name='page' value='${currentPage + 1}'/>
                      </c:url>">Next &raquo;</a>
        </c:if>
    </div>
</main>

<footer>
    <%@ include file="fragments/footer.jsp" %>
</footer>
</body>
</html>