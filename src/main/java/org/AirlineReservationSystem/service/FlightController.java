package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.model.Schedule;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/search")
public class FlightController {
    private final SearchService searchService;

    public FlightController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public String searchForm(Model m) {
        m.addAttribute("airports", airportRepo.findAll());
        return "searchForm";
    }

    @PostMapping("/results")
    public String results(@RequestParam String origin,
                          @RequestParam String dest,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                          Model m) {
        List<Schedule> flights = searchService.findByRouteAndDate(origin, dest, date);
        m.addAttribute("flights", flights);
        return "searchResults";
    }
}
