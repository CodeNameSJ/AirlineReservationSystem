package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepo;

    public FlightService(FlightRepository flightRepo) {
        this.flightRepo = flightRepo;
    }

    public List<Flight> getAllFlights() {
        return flightRepo.findAll();
    }

    public void save(Flight f) {
        flightRepo.save(f);
    }
}
