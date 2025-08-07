package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    long countByScheduleIdAndSeatNumber(Long scheduleId, Integer seatNumber);

    List<Booking> findByUserId(Long userId);

    List<Booking> findAllByUserOrderByBookingTimeDesc(User user);

    @Query("SELECT COALESCE(SUM(b.seatsBooked),0) FROM Booking b WHERE b.flight = :flight")
    Integer totalSeatsBookedByFlight(Flight flight);
}