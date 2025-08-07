package org.AirlineReservationSystem.controller;

<<<<<<< Updated upstream
import org.AirlineReservationSystem.dto.ScheduleDTO;
import org.AirlineReservationSystem.dto.SearchRequest;
import org.AirlineReservationSystem.model.Airport;
import org.AirlineReservationSystem.repository.AirportRepository;
import org.AirlineReservationSystem.service.SearchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/search")

public class FlightController {
    private final SearchService searchService;
    private final AirportRepository airportRepo;

    public FlightController(SearchService searchService, AirportRepository airportRepo) {
        this.searchService = searchService;
        this.airportRepo = airportRepo;
    }

    @GetMapping("/")
    public String searchForm(Model model) {
        model.addAttribute("searchRequest", new SearchRequest());
        return "search";
    }
// for dropdown menu on frontend
//    @GetMapping("/search")
//    public String showSearchPage(Model model) {
//        model.addAttribute("searchRequest", new SearchRequest());
//
//        List<Airport> airports = airportRepo.findAll();
//        model.addAttribute("airports", airports);
//
//        return "search";
//    }

//    @PostMapping("/search/results")
//    public String listResults(@RequestParam String origin,
//                              @RequestParam String dest,
//                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
//                              Model model) {
//        SearchRequest req = new SearchRequest();
//        req.setOriginCode(origin);
//        req.setDestinationCode(dest);
//        req.setFrom(date.atStartOfDay());
//        req.setTo(date.atTime(LocalTime.MAX));
//        List<ScheduleDTO> flights = searchService.searchSchedules(req);
//        model.addAttribute("flights", flights);
//        return "searchResults";
//    }

    @PostMapping("/results")
    public String results(@ModelAttribute SearchRequest req, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Model model) {
        req.setFrom(date.atStartOfDay());
        req.setTo(date.atTime(LocalTime.MAX));
        List<ScheduleDTO> list = searchService.searchSchedules(req);
        model.addAttribute("results", list);
        return "results";
=======
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
>>>>>>> Stashed changes
    }
}