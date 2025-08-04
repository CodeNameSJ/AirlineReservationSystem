package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
