package org.airlinereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.airlinereservationsystem.model.Flight;
import org.airlinereservationsystem.model.enums.SeatClass;
import org.airlinereservationsystem.service.BookingService;
import org.airlinereservationsystem.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final FlightService flightService;
	private final BookingService bookingService;

	// view bookings
	@GetMapping("/home")
	public String viewBookings(HttpServletRequest req, Model model) {
		HttpSession s = req.getSession(false);
		if (s == null || s.getAttribute("userId") == null) return "redirect:/login";
		Long userId = (Long) s.getAttribute("userId");
		model.addAttribute("bookings", bookingService.findByUserId(userId));
		return "user/home";
	}

	@GetMapping("/book")
	public String showBookingForm(@RequestParam Long flightId, HttpServletRequest req, Model model) {
		HttpSession s = req.getSession(false);
		if (s == null || s.getAttribute("userId") == null) {
			// save intended URL and redirect to log in
			String url = "/user/book?flightId=" + flightId;
			req.getSession(true).setAttribute("redirectAfterLogin", url);
			return "redirect:/login";
		}
		Flight f = flightService.findById(flightId).orElseThrow();
		model.addAttribute("flight", f);
		return "user/bookingForm";
	}

	@PostMapping("/book")
	public String doBook(@RequestParam Long flightId, @RequestParam int seats, HttpServletRequest req, Model model) {
		HttpSession s = req.getSession(false);
		if (s == null || s.getAttribute("userId") == null) {
			req.getSession(true).setAttribute("redirectAfterLogin", "/user/book?flightId=" + flightId);
			return "redirect:/login";
		}
		Long userId = (Long) s.getAttribute("userId");
		// call the updated booking method that requires userId
		var booking = bookingService.createBooking(userId, flightId, SeatClass.ECONOMY, seats);
		model.addAttribute("booking", booking);
		return "user/bookingSuccess";
	}

	@PostMapping("/bookings/cancel")
	public String cancel(@RequestParam Long id) {
		bookingService.cancelBooking(id);
		return "redirect:/user/home";
	}

	@GetMapping("/booking/{id}")
	public String bookingDetail(@PathVariable Long id, HttpServletRequest req, Model model) {
		HttpSession s = req.getSession(false);
		if (s == null || s.getAttribute("userId") == null) return "redirect:/login";
		Long userId = (Long) s.getAttribute("userId");
		var opt = bookingService.findById(id);
		if (opt.isEmpty()) return "redirect:/user/bookings";
		var booking = opt.get();
		// ensure booking belongs to logged user (safety)
		if (!booking.getUser().getId().equals(userId)) return "redirect:/user/bookings";
		model.addAttribute("booking", booking);
		return "user/bookingBill";
	}
}