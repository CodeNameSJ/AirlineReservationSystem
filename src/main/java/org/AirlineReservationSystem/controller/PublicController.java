package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.service.FlightService;
import org.AirlineReservationSystem.util.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PublicController {

	private final FlightService flightService;

	public PublicController(FlightService flightService) {
		this.flightService = flightService;
	}

	@GetMapping({"/", "/home"})
	public String home(Model model) {
		List<Flight> flights = flightService.findAll();
		addFormattedMaps(model, flights);
		model.addAttribute("flights", flights);
		return "home";
	}

	@GetMapping({"/flight-list"})
	public String flightList(Model model) {
		List<Flight> flights = flightService.findAll();
		addFormattedMaps(model, flights);
		model.addAttribute("flights", flights);
		return "flights";
	}

	@GetMapping("/flights")
	public String flights(@RequestParam(required = false) String origin, @RequestParam(required = false) String destination, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpServletRequest request, Model model) {

		List<Flight> results;
		if (date == null) {
			results = flightService.search(origin, destination, null, null);
		} else {
			LocalDateTime start = date.atStartOfDay();
			LocalDateTime end = date.atTime(LocalTime.MAX);
			results = flightService.search(origin, destination, start, end);
		}

		addFormattedMaps(model, results);
		model.addAttribute("flights", results);
		model.addAttribute("origin", origin);
		model.addAttribute("destination", destination);
		model.addAttribute("date", date);

		if ("true".equalsIgnoreCase(request.getParameter("ajax"))) {
			return "fragments/flights";
		}
		return "flights";
	}

	/**
	 * Flight details page — shows single flight details and a Book button.
	 */
	@GetMapping("/flight/{id}")
	public String flightDetails(@PathVariable("id") Long id, Model model) {
		Flight f = flightService.findById(id).orElse(null);
		if (f == null) {
			return "redirect:/flights";
		}
		model.addAttribute("flight", f);
		model.addAttribute("departureMap", DateUtils.formatForDisplay(f.getDepartureTime()));
		model.addAttribute("arrivalMap", DateUtils.formatForDisplay(f.getArrivalTime()));
		return "flightDetails";
	}

	private void addFormattedMaps(Model model, List<Flight> flights) {
		Map<Long, String> departureMap = new HashMap<>();
		Map<Long, String> arrivalMap = new HashMap<>();

		for (Flight f : flights) {
			departureMap.put(f.getId(), DateUtils.formatForDisplay(f.getDepartureTime()));
			arrivalMap.put(f.getId(), DateUtils.formatForDisplay(f.getArrivalTime()));
		}
		model.addAttribute("departureMap", departureMap);
		model.addAttribute("arrivalMap", arrivalMap);
	}
}