package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    int countByScheduleAndSeatNumber(Schedule schedule, int seatNo);
}