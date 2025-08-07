-- Sample users (passwords encoded via BCrypt)
-- Replace {bcrypt_admin} / {bcrypt_user} with actual hashes
INSERT INTO users (id, username, password, name, email, role) VALUES
																				 (1,'admin', '{bcrypt_admin}', 'Administrator', 'admin@example.com', 'ADMIN'),
																				 (2,'user', '{bcrypt_user}', 'Test User', 'user@example.com', 'USER');

-- Sample flights\INSERT INTO flights (flight_number, origin, destination, departure_time, total_economy_seats, total_business_seats, economy_seats_available, business_seats_available, price_economy, price_business) VALUES
('AI101', 'New York', 'London', '2025-09-01 08:00:00', 150, 20, 150, 20, 500.00, 1200.00),
('BA202', 'London', 'Paris', '2025-09-02 14:30:00', 100, 10, 100, 10, 150.00, 400.00),
('DL303', 'Paris', 'Rome', '2025-09-03 10:15:00', 120, 15, 120, 15, 200.00, 500.00);

-- Optionally, initial bookings
-- INSERT INTO bookings (user_id, flight_id, seat_class, seats, booking_time, status) VALUES
--   (2, 1, 'ECONOMY', 2, NOW(), 'BOOKED');