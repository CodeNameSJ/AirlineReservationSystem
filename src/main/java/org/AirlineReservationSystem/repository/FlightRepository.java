package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import java.time.LocalDateTime;
>>>>>>> Stashed changes
=======
import java.time.LocalDateTime;
>>>>>>> Stashed changes
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    List<Flight> findByOriginCodeAndDestinationCode(String originCode, String destinationCode);
=======
	List<Flight> findByOriginAndDestinationAndDepartureTimeBetween(String origin, String destination, LocalDateTime start, LocalDateTime end);
>>>>>>> Stashed changes
=======
	List<Flight> findByOriginAndDestinationAndDepartureTimeBetween(String origin, String destination, LocalDateTime start, LocalDateTime end);
>>>>>>> Stashed changes
}