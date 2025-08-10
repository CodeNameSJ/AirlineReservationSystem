package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class PublicController {

	private final FlightService flightService;

	public PublicController(FlightService flightService) {
		this.flightService = flightService;
	}

	@GetMapping({"/", "/home"})
	public String home(Model model) {
		List<Flight> flights = flightService.findAll();
		model.addAttribute("flights", flights);
		return "home";
	}

	// Flight search page — accessible without login
	@GetMapping("/flights")
	public String flights(@RequestParam(required = false) String origin, @RequestParam(required = false) String destination, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpServletRequest request, Model model) {

		LocalDateTime start = (date != null) ? date.atStartOfDay() : LocalDate.now().atStartOfDay();
		LocalDateTime end = (date != null) ? date.atTime(LocalTime.MAX) : LocalDate.now().atTime(LocalTime.MAX);

		List<Flight> results = flightService.search(origin, destination, start, end);
		model.addAttribute("flights", results);
		model.addAttribute("origin", origin);
		model.addAttribute("destination", destination);
		model.addAttribute("date", date);

		if ("true".equalsIgnoreCase(request.getParameter("ajax"))) {
			model.addAttribute("flights", results);
			return "fragments/flightList";
		}

		return "flights";
	}

	// Flight details page — public, shows details and "Book" button that goes to /user/book (protected)
	@GetMapping("/flight/{id}")
	public String flightDetails(@PathVariable("id") Long id, Model model) {
		Flight f = flightService.findById(id).orElse(null);
		if (f == null) {
			return "redirect:/flights";
		}
		model.addAttribute("flight", f);
		return "flights-details";
	}
}