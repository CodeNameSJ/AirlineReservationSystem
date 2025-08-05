<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav>
  <ul>
    <li><a href="<c:url value='/'/>">Home</a></li>
    <li><a href="<c:url value='/search/'/>">Search Flights</a></li>

    <c:choose>
      <c:when test="${not empty sessionScope.loggedInUser}">
        <li><a href="<c:url value='/booking/history'/>">My Bookings</a></li>
        <li><a href="<c:url value='/logout'/>">Logout</a></li>
        <li style="color: white;">Welcome, ${sessionScope.loggedInUser.username}</li>
      </c:when>
      <c:otherwise>
        <li><a href="<c:url value='/login'/>">Login</a></li>
        <li><a href="<c:url value='/register'/>">Register</a></li>
      </c:otherwise>
    </c:choose>
  </ul>
</nav>
