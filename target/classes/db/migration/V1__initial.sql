-- Flyway migration V1__initial.sql - initial schema for Airline Reservation System

CREATE TABLE IF NOT EXISTS `users`
(
	`id`       BIGINT       NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(255) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	`email`    VARCHAR(255) NOT NULL,
	`role`     VARCHAR(50)  NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `uk_users_username` (`username`),
	UNIQUE KEY `uk_users_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `flights`
(
	`id`                     BIGINT         NOT NULL AUTO_INCREMENT,
	`flightNumber`           VARCHAR(255)   NOT NULL,
	`origin`                 VARCHAR(255)   NOT NULL,
	`destination`            VARCHAR(255)   NOT NULL,
	`departureTime`          DATETIME       NOT NULL,
	`arrivalTime`            DATETIME       NOT NULL,
	`totalEconomySeats`      INT DEFAULT 0,
	`totalBusinessSeats`     INT DEFAULT 0,
	`economySeatsAvailable`  INT DEFAULT 0,
	`businessSeatsAvailable` INT DEFAULT 0,
	`priceEconomy`           DECIMAL(19, 2) NOT NULL,
	`priceBusiness`          DECIMAL(19, 2) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `uk_flights_flight_number` (`flightNumber`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `bookings`
(
	`id`          BIGINT      NOT NULL AUTO_INCREMENT,
	`user_id`     BIGINT      NOT NULL,
	`flight_id`   BIGINT      NOT NULL,
	`seatClass`   VARCHAR(50) NOT NULL,
	`seats`       INT         NOT NULL,
	`bookingTime` DATETIME    NOT NULL,
	`status`      VARCHAR(50) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `idx_bookings_user` (`user_id`),
	KEY `idx_bookings_flight` (`flight_id`),
	CONSTRAINT `fk_bookings_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `fk_bookings_flight` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- Seed roles (optional): if your application expects specific roles
INSERT INTO `users` (`username`, `password`, `email`, `role`)
VALUES ('admin', '{bcrypt_or_plain_password}', 'admin@mail.com', 'ADMIN'),
		 ('user', '{bcrypt_or_plain_password}', 'user@mail.com', 'USER');
-- Note: replace the password above with a hashed password if you plan to use Spring Security.