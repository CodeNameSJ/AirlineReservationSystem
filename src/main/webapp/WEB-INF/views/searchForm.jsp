<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="${pageContext.request.contextPath}/search/results" method="post">
    <label>
        <select name="origin">
            <c:forEach var="a" items="${airports}">
                <option value="${a.code}">${a.city} (${a.code})</option>
            </c:forEach>
        </select>
    </label>
    <button type="submit">Find Flights</button>
</form>
