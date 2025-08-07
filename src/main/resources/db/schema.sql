DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
							 id BIGINT AUTO_INCREMENT PRIMARY KEY,
							 username VARCHAR(50) NOT NULL UNIQUE,
							 password VARCHAR(100) NOT NULL,
							 name VARCHAR(100) NOT NULL,
							 email VARCHAR(100) NOT NULL UNIQUE,
							 role VARCHAR(20) NOT NULL
);

CREATE TABLE flights (
								id BIGINT AUTO_INCREMENT PRIMARY KEY,
								flight_number VARCHAR(20) NOT NULL UNIQUE,
								origin VARCHAR(100) NOT NULL,
								destination VARCHAR(100) NOT NULL,
								departure_time DATETIME NOT NULL,
								total_economy_seats INT NOT NULL,
								total_business_seats INT NOT NULL,
								economy_seats_available INT NOT NULL,
								business_seats_available INT NOT NULL,
								price_economy DECIMAL(10,2) NOT NULL,
								price_business DECIMAL(10,2) NOT NULL
);

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