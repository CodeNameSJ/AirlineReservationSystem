package org.airlinereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.airlinereservationsystem.model.Flight;
import org.airlinereservationsystem.service.FlightService;
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

import static org.airlinereservationsystem.util.DateUtils.addFormattedMaps;

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
	public String showFlights(@RequestParam(required = false) String origin, @RequestParam(required = false) String destination, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpServletRequest request, Model model) {

		List<Flight> flights;
		if (date == null) {
			flights = flightService.search(origin, destination, null, null);
		} else {
			LocalDateTime start = date.atStartOfDay();
			LocalDateTime end = date.atTime(LocalTime.MAX);
			flights = flightService.search(origin, destination, start, end);
		}

		addFormattedMaps(model, flights);
		model.addAttribute("flights", flights);
		model.addAttribute("origin", origin);
		model.addAttribute("destination", destination);
		model.addAttribute("date", date);

		if ("true".equalsIgnoreCase(request.getParameter("ajax"))) {
			return "fragments/flights";
		}
		return "flights";
	}

	@GetMapping("/flight/{id}")
	public String flightDetails(@PathVariable("id") Long id, Model model) {
		Flight f = flightService.findById(id).orElse(null);
		if (f == null) {
			return "redirect:/flights";
		}
		model.addAttribute("flight", f);
//		model.addAttribute("departureMap", DateUtils.formatForDisplay(f.getDepartureTime()));
//		model.addAttribute("arrivalMap", DateUtils.formatForDisplay(f.getArrivalTime()));
		return "flightDetails";
	}
}