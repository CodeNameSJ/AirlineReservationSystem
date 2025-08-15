<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8"/>
	<link rel="stylesheet" href="<c:url value='../css/style.css'/>"/>
	<title>Booking Bill</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			margin: 20px;
			background: #f5f5f5;
		}

		.bill-container {
			max-width: 800px;
			margin: auto;
			background: white;
			padding: 20px 30px;
			border-radius: 10px;
			box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
		}

		h1 {
			text-align: center;
			margin-bottom: 30px;
		}

		.section-title {
			background: #333;
			color: white;
			padding: 8px 10px;
			margin-top: 20px;
			border-radius: 5px;
			font-size: 18px;
		}

		table {
			width: 100%;
			border-collapse: collapse;
			margin-top: 10px;
		}

		th, td {
			border: 1px solid #ccc;
			padding: 8px 12px;
			text-align: left;
		}

		th {
			background: #f0f0f0;
		}

		.total {
			text-align: right;
			font-size: 18px;
			margin-top: 15px;
		}

		.print-btn {
			display: block;
			margin: 20px auto 0;
			padding: 8px 20px;
			background: #28a745;
			color: white;
			border: none;
			border-radius: 5px;
			cursor: pointer;
			font-size: 16px;
		}

		.print-btn:hover {
			background: #218838;
		}
	</style>
</head>
<body>
<main>
	<div class="bill-container">
		<h1>Booking Invoice</h1>

		<!-- Passenger Info -->
		<div class="section-title">Passenger Details</div>
		<table>
			<tr>
				<th>Name</th>
				<td>${booking.user.username}</td>
			</tr>
			<tr>
				<th>Email</th>
				<td>${booking.user.email}</td>
			</tr>
		</table>

		<!-- Flight Info -->
		<div class="section-title">Flight Details</div>
		<table>
			<tr>
				<th>Flight Number</th>
				<td>${booking.flight.flightNumber}</td>
			</tr>
			<tr>
				<th>From</th>
				<td>${booking.flight.origin}</td>
			</tr>
			<tr>
				<th>To</th>
				<td>${booking.flight.destination}</td>
			</tr>
			<tr>
				<th>Departure</th>
				<td>${booking.flight.departureTime}</td>
			</tr>
			<tr>
				<th>Arrival</th>
				<td>${booking.flight.arrivalTime}</td>
			</tr>
			<tr>
				<th>Class</th>
				<td>${booking.seatClass}</td>
			</tr>
		</table>

		<!-- Payment Info -->
		<div class="section-title">Payment Details</div>
		<table>
			<tr>
				<th>Base Fare</th>
				<td>₹${booking.baseFare}</td>
			</tr>
			<tr>
				<th>Taxes</th>
				<td>₹${booking.taxes}</td>
			</tr>
			<tr>
				<th>Other Charges</th>
				<td>₹${booking.otherCharges}</td>
			</tr>
		</table>

		<div class="total">
			<strong>Total Amount:</strong> ₹${booking.totalAmount}
		</div>

		<!-- Print Button -->
		<button class="print-btn" onclick="window.print()">Print Bill</button>
	</div>
</main>
<footer>
	<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>