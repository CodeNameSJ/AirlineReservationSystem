package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.model.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {
    public List<Schedule> findByRouteAndDate(String origin, String dest, LocalDate date) {
        return null;
    }
}
