INSERT INTO users (username, password, email, role) VALUES
																		 ('admin','adminpass','admin@mail.com','ADMIN'),
																		 ('user','pass','user@mail.com','USER');

INSERT INTO flights (flight_number, origin, destination, departure_time, arrival_time, total_economy_seats, total_business_seats, economy_seats_available, business_seats_available, price_economy, price_business)
VALUES
	('AI101','New Delhi','Mumbai','2025-09-10 08:30:00','2025-09-10 10:00:00',120,20,120,20,4500,9000),
	('BL201','Bengaluru','Chennai','2025-09-11 12:00:00','2025-09-11 13:00:00',100,10,100,10,2500,5000),
	('BL202','Bengaluru','Mumbai','2025-09-11 12:00:00','2025-09-11 13:00:00',100,10,100,10,2500,5000);

-- sample booking (assumes user id 2 and flight id 1)
INSERT INTO bookings (user_id, flight_id, seat_class, seats, booking_time, status) VALUES
	(2,1,'ECONOMY',2,NOW(),'BOOKED');