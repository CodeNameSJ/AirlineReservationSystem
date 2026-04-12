package org.AirlineReservationSystem.util;

import org.AirlineReservationSystem.model.enums.SeatClass;

public interface SeatReleaseProjection {
  Long getFlightId();

  SeatClass getSeatClass();

  Integer getTotalSeats();
}
