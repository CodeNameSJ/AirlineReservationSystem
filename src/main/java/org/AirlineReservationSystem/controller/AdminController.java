package org.AirlineReservationSystem.controller;

import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.service.BookingService;
import org.AirlineReservationSystem.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private final FlightService flightService;
	private final BookingService bookingService;

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("flights", flightService.findAll());
		return "admin/dashboard";
	}

	@GetMapping("/flights")
	public String manageFlights(Model model) {
		model.addAttribute("flights", flightService.findAll());
		return "admin/flights";
	}

	@GetMapping("/flights/new")
	public String showNewFlightForm(Model model) {
		model.addAttribute("flight", new Flight());
		return "admin/flightForm";
	}

	@PostMapping("/flights")
	public String saveFlight(@ModelAttribute Flight flight) {
		flightService.save(flight);
		return "redirect:/admin/flights";
	}

	@GetMapping("/flights/edit")
	public String showEditForm(@RequestParam Long id, Model model) {
		model.addAttribute("flight", flightService.findById(id).orElseThrow());
		return "admin/flightForm";
	}

	@PostMapping("/flights/delete")
	public String deleteFlight(@RequestParam Long id) {
		flightService.delete(id);
		return "redirect:/admin/flights";
	}

	@GetMapping("/bookings")
	public String manageBookings(Model model) {
		model.addAttribute("bookings", bookingService.findAll());
		return "admin/bookings";
	}

	@PostMapping("/bookings/cancel")
	public String cancel(@RequestParam Long id) {
		bookingService.cancelBooking(id);
		return "redirect:/admin/bookings";
	}
}