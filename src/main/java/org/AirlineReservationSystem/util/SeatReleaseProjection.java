package org.airlinereservationsystem.util;

import org.airlinereservationsystem.model.enums.SeatClass;

public interface SeatReleaseProjection {
	Long getFlightId();

	SeatClass getSeatClass();

	Integer getTotalSeats();
}