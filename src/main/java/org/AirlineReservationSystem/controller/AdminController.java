package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.service.BookingService;
import org.AirlineReservationSystem.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private final FlightService flightService;
	private final BookingService bookingService;
//	private final UserService userService;

	private boolean isAdmin(HttpServletRequest req) {
		HttpSession s = req.getSession(false);
		return s == null || !"ADMIN".equalsIgnoreCase(String.valueOf(s.getAttribute("role")));
	}

	@GetMapping("/dashboard")
	public String dashboard(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		model.addAttribute("flights", flightService.findAll());
		return "admin/dashboard";
	}

//	@GetMapping("/users")
//	public String users(HttpServletRequest req, Model model) {
//		if (isAdmin(req)) return "redirect:/login";
//		model.addAttribute("users", userService.findAll());
//		return "admin/users";
//	}

	@GetMapping("/flights")
	public String manageFlights(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";

		List<Flight> flights = flightService.findAll();

		flights.forEach(f -> {
			boolean hasBookings = bookingService.existsByFlightId(f.getId());
			f.setHasBookings(hasBookings);
		});

		model.addAttribute("flights", flights); // <-- use a modified list with hasBookings
		return "admin/flights";
	}

	@GetMapping("/flights/new")
	public String showNewFlightForm(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		model.addAttribute("flight", new Flight());
		return "admin/flightForm";
	}

	@PostMapping("/flights")
	public String saveFlight(HttpServletRequest req, @ModelAttribute Flight flight) {
		if (isAdmin(req)) return "redirect:/login";
		flightService.save(flight);
		return "redirect:/admin/flights";
	}

	@GetMapping("/flights/edit")
	public String showEditForm(HttpServletRequest req, @RequestParam Long id, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		Flight f = flightService.findById(id).orElseThrow();
		model.addAttribute("flight", f);

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		model.addAttribute("departureTimeStr", f.getDepartureTime().format(fmt));
		model.addAttribute("arrivalTimeStr", f.getArrivalTime().format(fmt));

		return "admin/flightForm";
	}

	/**
	 * Delete flight request. If a flight has bookings, show confirmation page with details
	 * otherwise delete it immediately.
	 */
	@PostMapping("/flights/delete")
	public String deleteFlightRequest(HttpServletRequest req, @RequestParam Long id, @RequestParam(required = false) boolean confirm, Model model, RedirectAttributes redirectAttributes) {
		if (isAdmin(req)) return "redirect:/login";

		if (confirm) {
			// optionally delete bookings first
			bookingService.deleteByFlightId(id);
			flightService.delete(id);
			redirectAttributes.addFlashAttribute("successMessage", "Flight deleted successfully.");
			return "redirect:/admin/flights";
		}

		if (bookingService.existsByFlightId(id)) {
			model.addAttribute("flightId", id);
			model.addAttribute("flight", flightService.findById(id).orElseThrow());
			model.addAttribute("hasBookings", true);
			return "admin/confirmFlightDelete";
		} else {
			flightService.delete(id);
			redirectAttributes.addFlashAttribute("successMessage", "Flight deleted successfully.");
			return "redirect:/admin/flights";
		}
	}

//
//	@GetMapping("/bookings")
//	public String manageBookings(HttpServletRequest req, Model model) {
//		if (isAdmin(req)) return "redirect:/login";
//		model.addAttribute("bookings", bookingService.findAll());
//		return "admin/bookings";
//	}
//
//	@PostMapping("/bookings/cancel")
//	public String cancel(HttpServletRequest req, @RequestParam Long id) {
//		if (isAdmin(req)) return "redirect:/login";
//		bookingService.cancelBooking(id);
//		return "redirect:/admin/bookings";
//	}
}