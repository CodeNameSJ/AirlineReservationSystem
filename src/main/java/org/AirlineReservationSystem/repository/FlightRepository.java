package org.airlinereservationsystem.repository;

import org.airlinereservationsystem.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
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
	int reserveEconomySeats(@Param("flightId") Long flightId, @Param("seats") int seats);


	@Modifying
	@Query("""
			UPDATE Flight f
			SET f.businessSeatsAvailable = f.businessSeatsAvailable - :seats
			WHERE f.id = :flightId AND f.businessSeatsAvailable >= :seats
			""")
	int reserveBusinessSeats(@Param("flightId") Long flightId, @Param("seats") int seats);

	@Modifying
	@Query("""
			UPDATE Flight f
			SET f.economySeatsAvailable = f.economySeatsAvailable + :seats
			WHERE f.id = :flightId
			""")
	void releaseEconomySeats(@Param("flightId") Long flightId, @Param("seats") int seats);


	@Modifying
	@Query("""
			UPDATE Flight f
			SET f.businessSeatsAvailable = f.businessSeatsAvailable + :seats
			WHERE f.id = :flightId
			""")
	void releaseBusinessSeats(@Param("flightId") Long flightId, @Param("seats") int seats);
}