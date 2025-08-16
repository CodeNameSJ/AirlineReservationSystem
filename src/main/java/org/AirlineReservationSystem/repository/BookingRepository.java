package org.airlinereservationsystem.repository;

import org.airlinereservationsystem.model.Booking;
import org.airlinereservationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUser(User user);

	List<Booking> findByUserId(Long userId);

	boolean existsByFlightId(Long flightId);

	void deleteByFlightId(Long flightId);

	void deleteByUserId(Long userId);

	boolean existsByUserId(Long userId);
}