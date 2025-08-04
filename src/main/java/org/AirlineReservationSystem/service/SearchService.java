package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.model.Airport;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.model.Schedule;
import org.AirlineReservationSystem.repository.AirportRepository;
import org.AirlineReservationSystem.repository.FlightRepository;
import org.AirlineReservationSystem.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchService {
    private final AirportRepository airportRepo;
    private final FlightRepository flightRepo;
    private final ScheduleRepository scheduleRepo;

    public SearchService(AirportRepository airportRepo, FlightRepository flightRepo, ScheduleRepository scheduleRepo) {
        this.airportRepo = airportRepo;
        this.flightRepo = flightRepo;
        this.scheduleRepo = scheduleRepo;
    }

    public List<Schedule> searchSchedules(String originCode, String destCode, LocalDateTime from, LocalDateTime to) {
        Airport origin = airportRepo.findByCode(originCode);
        Airport dest = airportRepo.findByCode(destCode);
        if (origin == null || dest == null) {
            throw new IllegalArgumentException("Invalid airport codes");
        }

        List<Flight> flights = flightRepo.findByOriginCodeAndDestinationCode(originCode, destCode);
        return flights.stream().flatMap(f -> scheduleRepo.findByFlightIdAndDepartureBetween(f.getId(), from, to).stream()).toList();
    }
}