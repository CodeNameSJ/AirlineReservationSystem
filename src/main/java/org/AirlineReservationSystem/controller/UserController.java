package org.AirlineReservationSystem.controller;

import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.SeatClass;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.service.BookingService;
import org.AirlineReservationSystem.service.FlightService;
import org.AirlineReservationSystem.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final FlightService flightService;
	private final BookingService bookingService;
	private final UserService userService;

	@GetMapping("/home")
	public String userHome(Model model) {
		model.addAttribute("flights", flightService.findAll());
		return "user/home";
	}

	@GetMapping("/login")
	public String login() {
		return "loginUser";
	}

	@GetMapping("/book")
	public String showBookingForm(@RequestParam Long flightId, Model model) {
		model.addAttribute("flight", flightService.findById(flightId).orElseThrow());
		model.addAttribute("seatClasses", SeatClass.values());
		return "user/bookingForm";
	}

	@PostMapping("/book")
	public String book(@AuthenticationPrincipal UserDetails ud, @RequestParam Long flightId, @RequestParam SeatClass seatClass, @RequestParam int seats, Model model) {
		User user = userService.findByUsername(ud.getUsername()).orElseThrow();
		var booking = bookingService.createBooking(user, flightId, seatClass, seats);
		model.addAttribute("booking", booking);
		return "user/bookingSuccess";
	}

	@GetMapping("/bookings")
	public String viewBookings(@AuthenticationPrincipal UserDetails ud, Model model) {
		User user = userService.findByUsername(ud.getUsername()).orElseThrow();
		model.addAttribute("bookings", bookingService.findByUser(user));
		return "user/bookings";
	}

	@PostMapping("/cancel")
	public String cancelBooking(@RequestParam Long bookingId) {
		bookingService.cancelBooking(bookingId);
		return "redirect:/user/bookings";
	}
}