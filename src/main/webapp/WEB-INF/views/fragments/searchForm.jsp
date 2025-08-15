<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<link rel="stylesheet" href="<c:url value='../css/search.css'/>">
<link rel="stylesheet" href="<c:url value='../css/form.css'/>">
<div class="flight-search-card">
	<form id="flightSearchForm" action="<c:url value='/flights'/>" method="get" class="flight-search-form">
		<div class="inline">
			<!-- Origin -->
			<div class="form-group">
				<label>Origin</label>
				<label>
					<select name="origin">
						<option value="">Select Airport</option>
						<option value="New Delhi" ${param.origin == 'New Delhi' ? 'selected' : ''}>New Delhi</option>
						<option value="Mumbai" ${param.origin == 'Mumbai' ? 'selected' : ''}>Mumbai</option>
						<option value="Bengaluru" ${param.origin == 'Bengaluru' ? 'selected' : ''}>Bengaluru</option>
						<option value="Chennai" ${param.origin == 'Chennai' ? 'selected' : ''}>Chennai</option>
					</select>
				</label>
			</div>

			<!-- Arrow Separator -->
			<span class="form-separator">&#8594;</span>

			<!-- Destination -->
			<div class="form-group">
				<label>Destination</label>
				<label>
					<select name="destination">
						<option value="">Select Airport</option>
						<option value="New Delhi" ${param.destination == 'New Delhi' ? 'selected' : ''}>New Delhi</option>
						<option value="Mumbai" ${param.destination == 'Mumbai' ? 'selected' : ''}>Mumbai</option>
						<option value="Bengaluru" ${param.destination == 'Bengaluru' ? 'selected' : ''}>Bengaluru</option>
						<option value="Chennai" ${param.destination == 'Chennai' ? 'selected' : ''}>Chennai</option>
					</select>
				</label>
			</div>
		</div>

		<!-- Date (optional) -->
		<div class="form-group">
			<label>Date</label>
			<label>
				<input type="date" name="date" value="${param.date}"/>
			</label>
		</div>
		<button type="submit" class="animated-button">Search Flights</button>
	</form>
</div>