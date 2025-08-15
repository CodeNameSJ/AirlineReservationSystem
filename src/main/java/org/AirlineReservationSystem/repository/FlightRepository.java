package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
	List<Flight> findByOriginAndDestinationAndDepartureTimeBetween(String origin, String destination, LocalDateTime start, LocalDateTime end);

	List<Flight> findByDestinationAndDepartureTimeBetween(String destination, LocalDateTime start, LocalDateTime end);

	List<Flight> findByDestination(String destination);

	List<Flight> findByOrigin(String origin);

	List<Flight> findByOriginAndDepartureTimeBetween(String origin, LocalDateTime start, LocalDateTime end);

	List<Flight> findByOriginAndDestination(String origin, String destination);

	List<Flight> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);
}