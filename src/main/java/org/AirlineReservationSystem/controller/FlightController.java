package org.AirlineReservationSystem.controller;

import org.AirlineReservationSystem.dto.ScheduleDTO;
import org.AirlineReservationSystem.dto.SearchRequest;
import org.AirlineReservationSystem.service.SearchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class FlightController {
    private final SearchService searchService;

    public FlightController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public String showSearchForm(Model model) {
        return "searchForm";
    }

    @PostMapping("/search/results")
    public String listResults(@RequestParam String origin,
                              @RequestParam String dest,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              Model model) {
        SearchRequest req = new SearchRequest();
        req.setOriginCode(origin);
        req.setDestinationCode(dest);
        req.setFrom(date.atStartOfDay());
        req.setTo(date.atTime(LocalTime.MAX));
        List<ScheduleDTO> flights = searchService.searchSchedules(req);
        model.addAttribute("flights", flights);
        return "searchResults";
    }
}