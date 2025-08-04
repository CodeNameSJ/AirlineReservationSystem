package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOriginCodeAndDestinationCode(String originCode, String destinationCode);
}