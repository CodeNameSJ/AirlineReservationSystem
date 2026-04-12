package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.AirlineReservationSystem.util.DateUtils.addFormattedMaps;

@Controller
public class PublicController {

	private final FlightService flightService;

	public PublicController(FlightService flightService) {
		this.flightService = flightService;
	}

	@GetMapping({"/", "/home"})
	public String home(Model model) {
		List<Flight> flights = flightService.findAll().stream().limit(10).toList();
		addFormattedMaps(model, flights);
		model.addAttribute("flights", flights);
		return "home";
	}

	@ModelAttribute("airports")
	public List<String> populateAirports() {
		return Stream.concat(flightService.originAirports().stream(), flightService.destinationAirports().stream()).filter(s -> s != null && !s.isBlank()).map(String::trim).distinct().sorted().toList();
	}

	@GetMapping("/flights")
	public String showFlights(@RequestParam(required = false) String origin, @RequestParam(required = false) String destination, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpServletRequest request, Model model) {

		LocalDateTime start = (date != null) ? date.atStartOfDay() : null;
		LocalDateTime end = (date != null) ? date.atTime(LocalTime.MAX) : null;

		List<Flight> flights = flightService.search(origin, destination, start, end);

		addFormattedMaps(model, flights);
		model.addAttribute("flights", flights);
		model.addAttribute("origin", origin);
		model.addAttribute("destination", destination);
		model.addAttribute("date", date);

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			model.addAttribute("fragment", "results");
			return "fragments/flights";
		}

		return "flights";
	}

	@GetMapping("/flight/{id}")
	public String flightDetails(@PathVariable Long id, Model model) {
		var opt = flightService.findById(id);
		if (opt.isEmpty()) return "redirect:/flights";

		model.addAttribute("flight", opt.get());
		return "flightDetails";
	}
}