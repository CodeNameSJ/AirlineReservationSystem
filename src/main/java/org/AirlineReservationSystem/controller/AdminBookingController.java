package org.airlinereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.airlinereservationsystem.model.Booking;
import org.airlinereservationsystem.model.enums.BookingStatus;
import org.airlinereservationsystem.model.enums.SeatClass;
import org.airlinereservationsystem.service.BookingService;
import org.airlinereservationsystem.service.FlightService;
import org.airlinereservationsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.airlinereservationsystem.util.IfAdmin.isNotAdmin;

@Controller
@RequestMapping("/admin/bookings")
public class AdminBookingController {

	private final BookingService bookingService;
	private final UserService userService;
	private final FlightService flightService;

	public AdminBookingController(BookingService bookingService, UserService userService, FlightService flightService) {
		this.bookingService = bookingService;
		this.userService = userService;
		this.flightService = flightService;
	}

	@GetMapping
	public String listBookings(HttpServletRequest req, Model model) {
		if (isNotAdmin(req))
			return "redirect:/login";
		model.addAttribute("bookings", bookingService.findAll());
		return "admin/bookings";
	}

	@GetMapping("/new")
	public String newBookingForm(HttpServletRequest req, Model model) {
		if (isNotAdmin(req))
			return "redirect:/login";
		Booking booking = new Booking();
		model.addAttribute("booking", booking);

		model.addAttribute("users", userService.findAll());
		model.addAttribute("flights", flightService.findAll());
		model.addAttribute("seatClasses", SeatClass.values());
		model.addAttribute("bookingStatus", BookingStatus.values());

		return "admin/booking-form";
	}

	@GetMapping("/edit/{id}")
	public String viewBooking(HttpServletRequest req, @PathVariable Long id, Model model) {
		if (isNotAdmin(req))
			return "redirect:/login";
		Optional<Booking> opt = bookingService.findById(id);
		if (opt.isEmpty())
			return "redirect:/admin/bookings";

		Booking booking = opt.get();
		model.addAttribute("booking", booking);

		model.addAttribute("users", userService.findAll());
		model.addAttribute("flights", flightService.findAll());
		model.addAttribute("seatClasses", SeatClass.values());
		model.addAttribute("bookingStatus", BookingStatus.values());
		return "admin/booking-form";
	}

	@PostMapping("/save")
	public String saveBooking(HttpServletRequest req, @RequestParam(required = false) Long id,
			@RequestParam Long userId, @RequestParam Long flightId, @RequestParam int seats,
			@RequestParam SeatClass seatClass, @RequestParam(required = false) BookingStatus status,
			Model model,
			RedirectAttributes ra) {
		if (isNotAdmin(req))
			return "redirect:/login";

		try {
			boolean isNewBooking = id == null;
			Booking booking = isNewBooking
					? bookingService.createBooking(userId, flightId, seatClass, seats)
					: bookingService.updateBooking(id, userId, flightId, seatClass, seats, status);
			String flightNumber = booking.getFlight() != null ? booking.getFlight().getFlightNumber() : "N/A";
			ra.addFlashAttribute("successMessage",
					(isNewBooking ? "Booking created" : "Booking updated") + ": #" + booking.getId() + " for flight "
							+ flightNumber + ".");
			return "redirect:/admin/bookings/edit/" + booking.getId();
		} catch (RuntimeException e) {
			Booking booking = new Booking();
			booking.setId(id);
			userService.findById(userId).ifPresent(booking::setUser);
			flightService.findById(flightId).ifPresent(booking::setFlight);
			booking.setSeatClass(seatClass);
			booking.setSeats(seats);
			booking.setStatus(status);

			model.addAttribute("errorMessage", e.getMessage());
			model.addAttribute("booking", booking);
			model.addAttribute("users", userService.findAll());
			model.addAttribute("flights", flightService.findAll());
			model.addAttribute("seatClasses", SeatClass.values());
			model.addAttribute("bookingStatus", BookingStatus.values());
			return "admin/booking-form";
		}
	}

	@PostMapping("/delete")
	public String deleteBooking(HttpServletRequest req, @RequestParam Long id,
			@RequestParam(required = false) boolean confirm, RedirectAttributes ra) {
		if (isNotAdmin(req))
			return "redirect:/login";

		if (confirm) {
			bookingService.delete(id);
			ra.addFlashAttribute("successMessage", "Booking deleted successfully.");
		}
		return "redirect:/admin/bookings";
	}

	@PostMapping("/cancel")
	public String cancelBooking(HttpServletRequest req, @RequestParam Long id,
			@RequestParam(required = false) boolean confirm, RedirectAttributes redirectAttributes) {
		if (isNotAdmin(req))
			return "redirect:/login";

		if (confirm) {
			bookingService.cancelBooking(id);
			redirectAttributes.addFlashAttribute("successMessage", "Booking canceled successfully.");
		}
		return "redirect:/admin/bookings";
	}
}
