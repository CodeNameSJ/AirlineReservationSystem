<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav>
    <ul>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/search'/>">Search Flights</a></li>

        <c:choose>
            <c:when test="${not empty currentUser}">
                <li><a href="<c:url value='/booking/history'/>">My Bookings</a></li>

                <c:if test="${currentUser.role.name eq 'ADMIN'}">
                    <li><a href="<c:url value='/admin/dashboard'/>">Admin Dashboard</a></li>
                </c:if>

                <li>
                    <form action="<c:url value='/logout'/>" method="post" style="display:inline;">
                        <button type="submit" style="background:none;border:none;color:white;cursor:pointer;">Logout
                        </button>
                    </form>
                </li>

                <li style="color: white;">Welcome, ${currentUser.username}!</li>
            </c:when>
            <c:otherwise>
                <li><a href="<c:url value='/login'/>">Login</a></li>
                <li><a href="<c:url value='/register'/>">Register</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>
