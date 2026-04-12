package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.service.BookingService;
import org.AirlineReservationSystem.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.AirlineReservationSystem.util.DateUtils.addFormattedMaps;
import static org.AirlineReservationSystem.util.IfAdmin.isNotAdmin;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final FlightService flightService;
	private final BookingService bookingService;

	public AdminController(FlightService flightService, BookingService bookingService) {
		this.flightService = flightService;
		this.bookingService = bookingService;
	}

	@GetMapping("/dashboard")
	public String dashboard(HttpServletRequest req, Model model) {
		if (isNotAdmin(req)) return "redirect:/login";
		List<Flight> flights = flightService.findAll();
		addFormattedMaps(model, flights);
		model.addAttribute("flights", flights);
		return "admin/dashboard";
	}

	@GetMapping("/flights")
	public String manageFlights(HttpServletRequest req, Model model) {
		if (isNotAdmin(req)) return "redirect:/login";

		List<Flight> flights = flightService.findAll();

		flights.forEach(f -> {
			boolean hasBookings = bookingService.existsByFlightId(f.getId());
			f.setHasBookings(hasBookings);
		});
		model.addAttribute("flights", flights);
		return "admin/flights";
	}

	@PostMapping("/flights")
	public String saveFlight(HttpServletRequest req, @ModelAttribute Flight flight, Model model, RedirectAttributes redirectAttributes) {
		if (isNotAdmin(req)) return "redirect:/login";
		boolean isNewFlight = flight.getId() == null;

		try {
			if (isNewFlight) {
				flight.setEconomySeatsAvailable(flight.getTotalEconomySeats());
				flight.setBusinessSeatsAvailable(flight.getTotalBusinessSeats());
			}

			flightService.save(flight);
			redirectAttributes.addFlashAttribute("successMessage", isNewFlight ? "Flight created successfully." : "Flight updated successfully.");
			return "redirect:/admin/flights";
		} catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
			model.addAttribute("flight", flight);
			return "admin/flight-form";
		}
	}

	@GetMapping("/flights/new")
	public String showNewFlightForm(HttpServletRequest req, Model model) {
		if (isNotAdmin(req)) return "redirect:/login";
		Flight flight = new Flight();
		model.addAttribute("flight", flight);

		model.addAttribute("departureTime", null);
		model.addAttribute("arrivalTime", null);

		return "admin/flight-form";
	}

	@GetMapping("/flights/edit/{flightNumber}")
	public String showEditFlightForm(@PathVariable String flightNumber, HttpServletRequest req, Model model) {
		if (isNotAdmin(req)) return "redirect:/login";
		var opt = flightService.findByFlightNumber(flightNumber);
		if (opt.isEmpty()) return "redirect:/admin/flights";
		Flight flight = opt.get();
		if (!flight.getFlightNumber().equals(flightNumber)) {
			return "redirect:/admin/flights/edit/" + flight.getFlightNumber();
		}
		model.addAttribute("flight", flight);
		return "admin/flight-form";
	}

	@PostMapping("/flights/delete")
	public String deleteFlightRequest(HttpServletRequest req, @RequestParam Long id, @RequestParam(required = false) boolean confirm, RedirectAttributes redirectAttributes) {
		if (isNotAdmin(req)) return "redirect:/login";

		if (confirm) {
			bookingService.deleteByFlightId(id);
			flightService.delete(id);
			redirectAttributes.addFlashAttribute("successMessage", "Flight deleted successfully.");
		}
		return "redirect:/admin/flights";
	}
}
