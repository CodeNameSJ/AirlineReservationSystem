package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOriginCodeAndDestinationCode(String originCode, String destinationCode);

    Page<Flight> findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCaseAndDepartureDate(
            String origin,
            String destination,
            LocalDate departureDate,
            Pageable pageable
    );

    // Fallback if no date filter:
    Page<Flight> findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCase(
            String origin,
            String destination,
            Pageable pageable
    );
}