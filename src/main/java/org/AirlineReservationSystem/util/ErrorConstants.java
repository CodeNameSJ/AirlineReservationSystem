package org.AirlineReservationSystem.util;

public enum ErrorConstants {
	NO_BOOKING_FOUND_ERROR("Booking not found"), NO_USER_FOUND_ERROR("User not found"),
	NO_FLIGHT_FOUND_ERROR("Flight not found"), SEAT_CLASS_REQUIRED_ERROR("Seat class required"),
	NEGATIVE_SEAT_COUNTS_ERROR("Seat counts cannot be negative"),
	SEATS_MUST_BE_GREATER_THAN_ZERO_ERROR("Seats must be greater than zero"),
	UNAUTHORIZED_CANCEL_ATTEMPT_ERROR("Unauthorized cancel attempt"),
	INVALID_BOOKING_DATA_ERROR("Invalid booking data for pricing"),
	FLIGHT_NUMBER_EXISTS_ERROR("Flight number already exists"),
	NOT_ENOUGH_SEATS_AVAILABLE_ERROR("Not enough seats available"),
	SEAT_RELEASE_EXCEEDS_CAPACITY_ERROR("Seat release exceeds capacity"),
	USERNAME_EXISTS_ERROR("Username already exists"), EMAIL_EXISTS_ERROR("Email already exists"),
	PASSWORD_REQUIRED_ERROR("Password required"), GENERIC_ERROR("Something went wrong");

	private final String message;

	ErrorConstants(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}