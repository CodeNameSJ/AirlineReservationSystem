package org.airlinereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.airlinereservationsystem.model.Flight;
import org.airlinereservationsystem.model.enums.SeatClass;
import org.airlinereservationsystem.service.BookingService;
import org.airlinereservationsystem.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
	private final FlightService flightService;
	private final BookingService bookingService;

	public UserController(FlightService flightService, BookingService bookingService) {
		this.flightService = flightService;
		this.bookingService = bookingService;
	}

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
		model.addAttribute("seatClasses", SeatClass.values());
		return "user/bookingForm";
	}

	@PostMapping("/book")
	public String doBook(@RequestParam Long flightId, @RequestParam int seats, @RequestParam SeatClass seatClass, HttpServletRequest req, Model model) {
		HttpSession s = req.getSession(false);
		if (s == null || s.getAttribute("userId") == null) {
			req.getSession(true).setAttribute("redirectAfterLogin", "/user/book?flightId=" + flightId);
			return "redirect:/login";
		}
		Long userId = (Long) s.getAttribute("userId");
		// call the updated booking method that requires userId
		var booking = bookingService.createBooking(userId, flightId, seatClass, seats);
		model.addAttribute("booking", booking);
		return "user/bookingSuccess";
	}

	@PostMapping("/bookings/cancel")
	public String cancel(@RequestParam Long id, HttpServletRequest req) {
		HttpSession s = req.getSession(false);
		if (s == null || s.getAttribute("userId") == null) return "redirect:/login";
		Long userId = (Long) s.getAttribute("userId");
		bookingService.cancelBookingForUser(id, userId);
		return "redirect:/user/home";
	}

	@GetMapping("/booking/{id}")
	public String bookingDetail(@PathVariable Long id, HttpServletRequest req, Model model) {
		HttpSession s = req.getSession(false);
		if (s == null || s.getAttribute("userId") == null) return "redirect:/login";
		Long userId = (Long) s.getAttribute("userId");
		var opt = bookingService.findById(id);
		if (opt.isEmpty()) return "redirect:/user/home";
		var booking = opt.get();
		// ensure booking belongs to logged user (safety)
		if (!booking.getUser().getId().equals(userId)) return "redirect:/user/home";
		model.addAttribute("booking", booking);
		return "user/bookingBill";
	}
}
