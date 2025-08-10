package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUser(User user);

	List<Booking> findByUserId(Long userId);
}