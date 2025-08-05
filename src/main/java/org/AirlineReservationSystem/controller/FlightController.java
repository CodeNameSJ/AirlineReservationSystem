package org.AirlineReservationSystem.controller;

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
    }
}