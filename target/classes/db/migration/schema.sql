DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS users;
-- create users
CREATE TABLE users (
							 id BIGINT AUTO_INCREMENT PRIMARY KEY,
							 username VARCHAR(100) NOT NULL UNIQUE,
							 password VARCHAR(255) NOT NULL,
							 email VARCHAR(255) NOT NULL UNIQUE,
							 role VARCHAR(20) NOT NULL
);

-- create flights (simple)
CREATE TABLE flights (
								id BIGINT AUTO_INCREMENT PRIMARY KEY,
								flight_number VARCHAR(50) UNIQUE,
								origin VARCHAR(100) NOT NULL,
								destination VARCHAR(100) NOT NULL,
								departure_time DATETIME NOT NULL,
								arrival_time DATETIME NOT NULL,
								total_economy_seats INT,
								total_business_seats INT,
								economy_seats_available INT,
								business_seats_available INT,
								price_economy DECIMAL(10,2),
								price_business DECIMAL(10,2),
								price DOUBLE
);

-- create bookings
CREATE TABLE bookings (
								id BIGINT AUTO_INCREMENT PRIMARY KEY,
								user_id BIGINT NOT NULL,
								flight_id BIGINT NOT NULL,
								seat_class VARCHAR(20) NOT NULL,
								seats INT NOT NULL,
								booking_time DATETIME NOT NULL,
								status VARCHAR(20) NOT NULL,
								FOREIGN KEY (user_id) REFERENCES users(id),
								FOREIGN KEY (flight_id) REFERENCES flights(id)
);