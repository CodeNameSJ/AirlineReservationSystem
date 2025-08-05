package org.AirlineReservationSystem.controller;

import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public String listFlights(
            @RequestParam Optional<String> origin,
            @RequestParam Optional<String> destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date,
            @RequestParam Optional<Integer> page,
            Model model
    ) {
        int currentPage = page.orElse(1) - 1;
        Page<Flight> flights = flightService.searchFlights(
                origin, destination, date, PageRequest.of(currentPage, PAGE_SIZE)
        );

        model.addAttribute("flights", flights.getContent());
        model.addAttribute("totalPages", flights.getTotalPages());
        model.addAttribute("currentPage", currentPage + 1);
        model.addAttribute("origin", origin.orElse(""));
        model.addAttribute("destination", destination.orElse(""));
        model.addAttribute("date", date.map(LocalDate::toString).orElse(""));

        return "flight-list";
    }
}