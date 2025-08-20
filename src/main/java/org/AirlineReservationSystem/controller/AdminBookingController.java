//package org.airlinereservationsystem.controller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.airlinereservationsystem.model.Booking;
//import org.airlinereservationsystem.model.enums.BookingStatus;
//import org.airlinereservationsystem.model.enums.SeatClass;
//import org.airlinereservationsystem.service.BookingService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("/admin/bookings")
//@RequiredArgsConstructor
//public class AdminBookingController {
//
//	private final BookingService bookingService;
//
//	private boolean isAdmin(HttpServletRequest req) {
//		HttpSession s = req.getSession(false);
//		return s == null || !"ADMIN".equalsIgnoreCase(String.valueOf(s.getAttribute("role")));
//	}
//
//	@GetMapping
//	public String listBookings(HttpServletRequest req, Model model) {
//		if (isAdmin(req)) return "redirect:/login";
//		model.addAttribute("bookings", bookingService.findAll());
//		return "admin/bookings";
//	}
//
//	@GetMapping("/edit/{id}")
//	public String viewBooking(HttpServletRequest req, @PathVariable Long id, Model model) {
//		if (isAdmin(req)) return "redirect:/login";
//		var opt = bookingService.findById(id);
//		if (opt.isEmpty()) return "redirect:/admin/bookings";
//		model.addAttribute("booking", opt.get());
//		model.addAttribute("bookingStatus", BookingStatus.values());
//		model.addAttribute("seatClasses", SeatClass.values());
//		return "admin/booking-form";
//	}
//
//	@PostMapping("/update")
//	public String updateBooking(HttpServletRequest req, @ModelAttribute Booking booking) {
//		if (isAdmin(req)) return "redirect:/login";
//		bookingService.save(booking);
//		return "redirect:/admin/bookings";
//	}
//
//	@PostMapping("/delete")
//	public String deleteBooking(HttpServletRequest req, @RequestParam Long id, @RequestParam(required = false) boolean confirm, RedirectAttributes ra) {
//		if (isAdmin(req)) return "redirect:/login";
//
//		if (confirm) {
//			bookingService.delete(id);
//			ra.addFlashAttribute("successMessage", "Booking deleted successfully.");
//		}
//		return "redirect:/admin/bookings";
//	}
//
//
//	@PostMapping("/cancel")
//	public String cancelBooking(HttpServletRequest req, @RequestParam Long id, @RequestParam(required = false) boolean confirm, RedirectAttributes redirectAttributes) {
//		if (isAdmin(req)) return "redirect:/login";
//
//		if (confirm) {
//			bookingService.cancelBooking(id);
//			redirectAttributes.addFlashAttribute("successMessage", "Booking Canceled successfully.");
//		}
//		return "redirect:/admin/bookings";
//	}
//}

package org.airlinereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.airlinereservationsystem.model.Booking;
import org.airlinereservationsystem.model.Flight;
import org.airlinereservationsystem.model.User;
import org.airlinereservationsystem.model.enums.BookingStatus;
import org.airlinereservationsystem.model.enums.SeatClass;
import org.airlinereservationsystem.service.BookingService;
import org.airlinereservationsystem.service.FlightService;
import org.airlinereservationsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/bookings")
@RequiredArgsConstructor
public class AdminBookingController {

	private final BookingService bookingService;
	private final UserService userService;
	private final FlightService flightService;

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

	@GetMapping("/new")
	public String newBookingForm(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";

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
		if (isAdmin(req)) return "redirect:/login";
		Optional<Booking> opt = bookingService.findById(id);
		if (opt.isEmpty()) return "redirect:/admin/bookings";

		Booking booking = opt.get();
		model.addAttribute("booking", booking);

		model.addAttribute("users", userService.findAll());
		model.addAttribute("flights", flightService.findAll());
		model.addAttribute("seatClasses", SeatClass.values());
		model.addAttribute("bookingStatus", BookingStatus.values());
		return "admin/booking-form";
	}

	@PostMapping("/save")
	public String saveBooking(HttpServletRequest req,
	                          @RequestParam(required = false) Long id,
	                          @RequestParam Long userId,
	                          @RequestParam Long flightId,
	                          @RequestParam int seats,
	                          @RequestParam SeatClass seatClass,
	                          @RequestParam(required = false) BookingStatus status,
	                          RedirectAttributes ra) {
		if (isAdmin(req)) return "redirect:/login";

		// fetch related entities
		Optional<User> uOpt = userService.findById(userId);
		Optional<Flight> fOpt = flightService.findById(flightId);
		if (uOpt.isEmpty() || fOpt.isEmpty()) {
			ra.addFlashAttribute("errorMessage", "Selected user or flight not found.");
			return "redirect:/admin/bookings";
		}

//		Booking booking;
//		if (id != null) {
//			booking = bookingService.findById(id).orElse(new Booking());
//		} else {
//			booking = new Booking();
//		}
//
//		booking.setUser(uOpt.get());
//		booking.setFlight(fOpt.get());
//		booking.setSeats(seats);
//		booking.setSeatClass(seatClass);

//		if (id == null) {
//			booking.setStatus(status == null ? BookingStatus.BOOKED : status);
//		} else {
//			if (status != null) booking.setStatus(status);
//		}

//		bookingService.save(booking);
		var booking = bookingService.createBooking(userId, flightId, seatClass, seats);
		ra.addFlashAttribute("successMessage", "Booking saved.");
		return "redirect:/admin/bookings";
	}

	@PostMapping("/delete")
	public String deleteBooking(HttpServletRequest req, @RequestParam Long id, @RequestParam(required = false) boolean confirm, RedirectAttributes ra) {
		if (isAdmin(req)) return "redirect:/login";

		if (confirm) {
			bookingService.delete(id);
			ra.addFlashAttribute("successMessage", "Booking deleted successfully.");
		}
		return "redirect:/admin/bookings";
	}

	@PostMapping("/cancel")
	public String cancelBooking(HttpServletRequest req, @RequestParam Long id, @RequestParam(required = false) boolean confirm, RedirectAttributes redirectAttributes) {
		if (isAdmin(req)) return "redirect:/login";

		if (confirm) {
			bookingService.cancelBooking(id);
			redirectAttributes.addFlashAttribute("successMessage", "Booking canceled successfully.");
		}
		return "redirect:/admin/bookings";
	}
}