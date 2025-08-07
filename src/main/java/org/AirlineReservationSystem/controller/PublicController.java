package org.AirlineReservationSystem.controller;

import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
public class PublicController {
	private final FlightService flightService;

	@GetMapping({"/", "/home"})
	public String home(Model model) {
		model.addAttribute("flights", flightService.findAll());
		return "home";
	}

	@GetMapping("/flights")
	public String search(@RequestParam(required = false) String origin, @RequestParam(required = false) String destination, @RequestParam(required = false) LocalDate date, Model model) {
		LocalDateTime start = date != null ? date.atStartOfDay() : LocalDate.now().atStartOfDay();
		LocalDateTime end = date != null ? date.atTime(LocalTime.MAX) : LocalDate.now().atTime(LocalTime.MAX);
		model.addAttribute("flights", flightService.search(origin, destination, start, end));
		return "flights";
	}

}