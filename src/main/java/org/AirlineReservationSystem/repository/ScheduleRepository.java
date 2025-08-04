package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByFlightIdAndDepartureBetween(Long flightId, LocalDateTime start, LocalDateTime end);
}