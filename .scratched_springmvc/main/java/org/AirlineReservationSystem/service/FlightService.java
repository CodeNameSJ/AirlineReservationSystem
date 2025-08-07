package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.repository.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Page<Flight> searchFlights(
            Optional<String> originOpt,
            Optional<String> destinationOpt,
            Optional<LocalDate> dateOpt,
            Pageable pageable
    ) {
        String origin = originOpt.orElse("");
        String destination = destinationOpt.orElse("");

        if (dateOpt.isPresent()) {
            return flightRepo.findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCaseAndDepartureDate(
                    origin, destination, dateOpt.get(), pageable
            );
        } else {
            return flightRepo.findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCase(
                    origin, destination, pageable
            );
        }
    }
}
