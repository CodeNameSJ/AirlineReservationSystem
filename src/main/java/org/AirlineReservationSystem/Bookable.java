package org.AirlineReservationSystem;

interface Bookable {
	void book(Passenger passenger);

	void cancel(Passenger passenger);

	void showAllBookings();
}