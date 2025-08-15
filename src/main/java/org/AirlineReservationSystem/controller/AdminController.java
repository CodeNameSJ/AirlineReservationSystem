package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.service.BookingService;
import org.AirlineReservationSystem.service.FlightService;
import org.AirlineReservationSystem.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

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
		model.addAttribute("flights", flightService.findAll());
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
	public String saveFlight(HttpServletRequest req, @RequestParam(required = false) Long id, @RequestParam String flightNumber, @RequestParam String origin, @RequestParam String destination, @RequestParam(required = false) String departureTimeInput, @RequestParam(required = false) String arrivalTimeInput, @RequestParam(required = false, defaultValue = "0") Integer totalEconomySeats, @RequestParam(required = false, defaultValue = "0") Integer totalBusinessSeats, @RequestParam(required = false) java.math.BigDecimal priceEconomy, @RequestParam(required = false) java.math.BigDecimal priceBusiness) {
		if (isAdmin(req)) return "redirect:/login";

		Flight flight;
		if (id != null) {
			flight = flightService.findById(id).orElse(new Flight());
		} else {
			flight = new Flight();
		}

		flight.setFlightNumber(flightNumber);
		flight.setOrigin(origin);
		flight.setDestination(destination);

		LocalDateTime dep = null;
		LocalDateTime arr = null;
		try {
			dep = DateUtils.parseFromInput(departureTimeInput);
		} catch (Exception _) {
		}
		try {
			arr = DateUtils.parseFromInput(arrivalTimeInput);
		} catch (Exception ex) {
			// handle parse error
		}
		flight.setDepartureTime(dep);
		flight.setArrivalTime(arr);

		flight.setTotalEconomySeats(totalEconomySeats == null ? 0 : totalEconomySeats);
		flight.setTotalBusinessSeats(totalBusinessSeats == null ? 0 : totalBusinessSeats);
		// initialize availability if new flight
		if (flight.getId() == null) {
			flight.setEconomySeatsAvailable(flight.getTotalEconomySeats());
			flight.setBusinessSeatsAvailable(flight.getTotalBusinessSeats());
		}

		flight.setPriceEconomy(priceEconomy);
		flight.setPriceBusiness(priceBusiness);

		flightService.save(flight);
		return "redirect:/admin/flights";
	}

	@GetMapping("/flights/new")
	public String showNewFlightForm(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		Flight flight = new Flight();
		model.addAttribute("flight", flight);
		// empty input values for new flight
		model.addAttribute("departureInput", "");
		model.addAttribute("arrivalInput", "");
		return "admin/flightForm";
	}

	@GetMapping("/flights/edit")
	public String showEditForm(HttpServletRequest req, @RequestParam Long id, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		Flight f = flightService.findById(id).orElseThrow();
		model.addAttribute("flight", f);

		String dep = org.AirlineReservationSystem.util.DateUtils.formatForInput(f.getDepartureTime());
		String arr = org.AirlineReservationSystem.util.DateUtils.formatForInput(f.getArrivalTime());

		model.addAttribute("departureInput", dep);
		model.addAttribute("arrivalInput", arr);

		return "admin/flightForm";
	}

	/**
	 * Delete flight request. If a flight has bookings, show confirmation page with details
	 * otherwise delete it immediately.
	 */
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