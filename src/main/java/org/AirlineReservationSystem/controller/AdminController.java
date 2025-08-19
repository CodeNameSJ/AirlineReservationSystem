package org.airlinereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.airlinereservationsystem.model.Flight;
import org.airlinereservationsystem.service.BookingService;
import org.airlinereservationsystem.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.airlinereservationsystem.util.DateUtils.addFormattedMaps;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private final FlightService flightService;
	private final BookingService bookingService;


	private boolean isAdmin(HttpServletRequest req) {
		HttpSession s = req.getSession(false);
		return s == null || !"ADMIN".equalsIgnoreCase(String.valueOf(s.getAttribute("role")));
	}

	@GetMapping("/dashboard")
	public String dashboard(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		List<Flight> flights = flightService.findAll();
		addFormattedMaps(model, flights);
		model.addAttribute("flights", flights);
		return "admin/dashboard";
	}

	@GetMapping("/flights")
	public String manageFlights(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";

		List<Flight> flights = flightService.findAll();

		flights.forEach(f -> {
			boolean hasBookings = bookingService.existsByFlightId(f.getId());
			f.setHasBookings(hasBookings);
		});
		model.addAttribute("flights", flights);
		return "admin/flights";
	}

	@PostMapping("/flights")
	public String saveFlight(HttpServletRequest req, @ModelAttribute Flight flight) {
		if (isAdmin(req)) return "redirect:/login";

		if (flight.getId() == null) {
			flight.setEconomySeatsAvailable(flight.getTotalEconomySeats());
			flight.setBusinessSeatsAvailable(flight.getTotalBusinessSeats());
		}

		flightService.save(flight);
		return "redirect:/admin/flights";
	}

	@GetMapping("/flights/new")
	public String showNewFlightForm(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		Flight flight = new Flight();
		model.addAttribute("flight", flight);

		model.addAttribute("departureTime", null);
		model.addAttribute("arrivalTime", null);

		return "admin/flight-form";
	}

	@GetMapping("/flights/edit/{id}")
	public String showEditFlightForm(@PathVariable Long id, HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		var opt = flightService.findById(id);
		if (opt.isEmpty()) return "redirect:/admin/flights";
		model.addAttribute("flight", opt.get());
		return "admin/flight-form";
	}

	@PostMapping("/flights/delete")
	public String deleteFlightRequest(HttpServletRequest req, @RequestParam Long id, @RequestParam(required = false) boolean confirm, RedirectAttributes redirectAttributes) {
		if (isAdmin(req)) return "redirect:/login";

		if (confirm) {
			bookingService.deleteByFlightId(id);
			flightService.delete(id);
			redirectAttributes.addFlashAttribute("successMessage", "Flight deleted successfully.");
		}
		return "redirect:/admin/flights";
	}
}