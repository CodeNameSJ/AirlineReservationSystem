package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/bookings")
@RequiredArgsConstructor
public class AdminBookingController {

	private final BookingService bookingService;

	private boolean isAdmin(HttpServletRequest req) {
		HttpSession s = req.getSession(false);
		return s == null || !"ADMIN".equalsIgnoreCase(String.valueOf(s.getAttribute("role")));
	}

	@GetMapping
	public String listBookings(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		model.addAttribute("bookings", bookingService.findAll());
		return "admin/bookings";
	}

	@GetMapping("/view/{id}")
	public String viewBooking(HttpServletRequest req, @PathVariable Long id, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		var opt = bookingService.findById(id);
		if (opt.isEmpty()) return "redirect:/admin/bookings";
		model.addAttribute("booking", opt.get());
		return "admin/booking-form";
	}

	@PostMapping("/update")
	public String updateBooking(HttpServletRequest req, @ModelAttribute Booking booking) {
		if (isAdmin(req)) return "redirect:/login";
		bookingService.save(booking);
		return "redirect:/admin/bookings";
	}

	@GetMapping("/delete/{id}")
	public String deleteBooking(HttpServletRequest req, @PathVariable Long id) {
		if (isAdmin(req)) return "redirect:/login";
		bookingService.delete(id);
		return "redirect:/admin/bookings";
	}

	@PostMapping("/cancel")
	public String cancelBooking(HttpServletRequest req, @RequestParam Long id) {
		if (isAdmin(req)) return "redirect:/login";
		bookingService.cancelBooking(id);
		return "redirect:/admin/bookings";
	}
}