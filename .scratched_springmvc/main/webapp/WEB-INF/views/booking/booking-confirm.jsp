<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head><title>Booking Confirmed</title></head>
<body>
<%@ include file="../fragments/header.jsp" %>

<h2>Your booking was successful!</h2>
<p>You can view your tickets in your dashboard.</p>
<a href="<c:url value='/dashboard'/>">Go to Dashboard</a>

<%@ include file="../fragments/footer.jsp" %>
</body>
</html>
