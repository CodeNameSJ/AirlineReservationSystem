package org.AirlinesReservationSystem;

interface Bookable {
	void book(Passenger passenger);

	void cancel(Passenger passenger);

	void showAllBookings();
}