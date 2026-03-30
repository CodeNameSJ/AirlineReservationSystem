package org.airlinereservationsystem.repository;

import org.airlinereservationsystem.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
	Optional<Flight> findByFlightNumber(String flightNumber);

	List<Flight> findByOriginAndDestinationAndDepartureTimeBetween(String origin, String destination, LocalDateTime start, LocalDateTime end);

	List<Flight> findByDestinationAndDepartureTimeBetween(String destination, LocalDateTime start, LocalDateTime end);

	List<Flight> findByDestination(String destination);

	@Query("select distinct f.origin from Flight f where f.origin is not null")
	List<String> findDistinctOrigins();

	@Query("select distinct f.destination from Flight f where f.destination is not null")
	List<String> findDistinctDestinations();

	List<Flight> findByOrigin(String origin);

	List<Flight> findByOriginAndDepartureTimeBetween(String origin, LocalDateTime start, LocalDateTime end);

	List<Flight> findByOriginAndDestination(String origin, String destination);

	List<Flight> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);

	@Modifying
	@Query("""
			    UPDATE Flight f
			    SET f.economySeatsAvailable = f.economySeatsAvailable - :seats
			    WHERE f.id = :flightId AND f.economySeatsAvailable >= :seats
			""")
	int reserveEconomySeats(Long flightId, int seats);


	@Modifying
	@Query("""
			    UPDATE Flight f
			    SET f.businessSeatsAvailable = f.businessSeatsAvailable - :seats
			    WHERE f.id = :flightId AND f.businessSeatsAvailable >= :seats
			""")
	int reserveBusinessSeats(Long flightId, int seats);


	@Modifying
	@Query("""
			    UPDATE Flight f
			    SET f.economySeatsAvailable = f.economySeatsAvailable + :seats
			    WHERE f.id = :flightId
			    AND f.economySeatsAvailable + :seats <= f.totalEconomySeats
			""")
	int releaseEconomySeats(Long flightId, int seats);


	@Modifying
	@Query("""
			    UPDATE Flight f
			    SET f.businessSeatsAvailable = f.businessSeatsAvailable + :seats
			    WHERE f.id = :flightId
			    AND f.businessSeatsAvailable + :seats <= f.totalBusinessSeats
			""")
	int releaseBusinessSeats(Long flightId, int seats);
}
