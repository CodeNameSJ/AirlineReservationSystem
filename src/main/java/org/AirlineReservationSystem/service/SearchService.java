package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.dto.ScheduleDTO;
import org.AirlineReservationSystem.dto.SearchRequest;
import org.AirlineReservationSystem.repository.AirportRepository;
import org.AirlineReservationSystem.repository.FlightRepository;
import org.AirlineReservationSystem.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ScheduleDTO> searchSchedules(SearchRequest req) {
        var flights = flightRepo.findByOrigin_CodeAndDestination_Code(req.getOriginCode(), req.getDestinationCode());
        return flights.stream()
                .flatMap(f -> scheduleRepo.findByFlightIdAndDepartureBetween(f.getId(), req.getFrom(), req.getTo()).stream())
                .map(s -> new ScheduleDTO(
                        s.getId(), f.getOrigin().getCode(), f.getDestination().getCode(),
                        s.getDeparture(), s.getArrival(), s.getBasePrice()))
                .collect(Collectors.toList());
    }
}