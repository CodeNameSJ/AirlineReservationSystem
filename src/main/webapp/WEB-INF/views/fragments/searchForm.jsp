<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<h1 class="page-title">Search Flights</h1>
<div class="search-card">
	<form action="${pageContext.request.contextPath}/flights" method="get" class="flight-search-form">

		<!-- Origin -->
		<div class="form-group">
			<label>Origin</label>
			<label>
				<select name="origin" required>
					<option value="">Select FROM Airport</option>
					<option value="DEL" ${param.origin == 'DEL' ? 'selected' : ''}>Delhi (DEL)</option>
					<option value="BOM" ${param.origin == 'BOM' ? 'selected' : ''}>Mumbai (BOM)</option>
					<option value="BLR" ${param.origin == 'BLR' ? 'selected' : ''}>Bengaluru (BLR)</option>
				</select>
			</label>
			<!-- optional free text input -->
			<%--			<label>--%>
			<%--				<input type="text" name="originText" value="${param.originText}" placeholder="Or enter departure city">--%>
			<%--			</label>--%>
		</div>

		<!-- Arrow Separator -->
		<span class="form-separator">&#8594;</span>

		<!-- Destination -->
		<div class="form-group">
			<label>Destination</label>
			<label>
				<select name="destination" required>
					<option value="">Select TO Airport</option>
					<option value="DEL" ${param.destination == 'DEL' ? 'selected' : ''}>Delhi (DEL)</option>
					<option value="BOM" ${param.destination == 'BOM' ? 'selected' : ''}>Mumbai (BOM)</option>
					<option value="BLR" ${param.destination == 'BLR' ? 'selected' : ''}>Bengaluru (BLR)</option>
				</select>
			</label>
			<!-- optional free text input -->
			<%--			<label>--%>
			<%--				<input type="text" name="destinationText" value="${param.destinationText}"--%>
			<%--				       placeholder="Or enter arrival city">--%>
			<%--			</label>--%>
		</div>

		<!-- Date -->
		<div class="form-group">
			<label>Date</label>
			<label>
				<input type="date" name="date" value="${param.date}" required>
			</label>
		</div>
		<br>
		<!-- Button -->
		<button type="submit" class="btn">Search Flights</button>
	</form>
</div>