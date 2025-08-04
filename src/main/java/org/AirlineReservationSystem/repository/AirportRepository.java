package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Airport findByCode(String code);
}