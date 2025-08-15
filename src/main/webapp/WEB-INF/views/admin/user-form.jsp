<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<h2><c:choose><c:when test="${not empty user.id}">Edit User</c:when><c:otherwise>Add User</c:otherwise></c:choose></h2>

<form action="${pageContext.request.contextPath}/admin/users/save" method="post">
	<c:if test="${not empty user.id}">
		<input type="hidden" name="id" value="${user.id}"/>
	</c:if>
	Username: <label>
	<input type="text" name="username" value="${user.username}" required/>
</label><br/>
	Email: <label>
	<input type="email" name="email" value="${user.email}" required/>
</label><br/>
	Password: <label>
	<input type="password" name="password" value="" ${not empty user.id ? '' : 'required'}/>
</label><br/>
	Role:
	<label>
		<select name="role">
			<option value="USER" ${user.role == 'USER' ? 'selected' : ''}>USER</option>
			<option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
		</select>
	</label><br/>
	<button type="submit">Save</button>
</form>