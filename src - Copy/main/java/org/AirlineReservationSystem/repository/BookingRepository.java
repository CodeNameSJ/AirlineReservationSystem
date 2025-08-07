package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    long countByScheduleIdAndSeatNumber(Long scheduleId, Integer seatNumber);

    List<Booking> findByUserId(Long userId);
}