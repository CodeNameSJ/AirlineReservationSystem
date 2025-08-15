package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.service.BookingService;
import org.AirlineReservationSystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

	private final UserService userService;
	private final BookingService bookingService;

	// returns true if the current session belongs to an ADMIN
	private boolean isAdmin(HttpServletRequest req) {
		HttpSession s = req.getSession(false);
		return s == null || !"ADMIN".equalsIgnoreCase(String.valueOf(s.getAttribute("role")));
	}

	@GetMapping
	public String listUsers(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		model.addAttribute("users", userService.findAll());
		return "admin/users";
	}

	@GetMapping("/add")
	public String showAddForm(HttpServletRequest req, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		model.addAttribute("user", new User());
		return "admin/user-form";
	}

	@PostMapping("/save")
	public String saveUser(HttpServletRequest req, @ModelAttribute User user) {
		if (isAdmin(req)) return "redirect:/login";
		userService.save(user);
		return "redirect:/admin/users";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(HttpServletRequest req, @PathVariable Long id, Model model) {
		if (isAdmin(req)) return "redirect:/login";
		var opt = userService.findById(id);
		if (opt.isEmpty()) return "redirect:/admin/users";
		model.addAttribute("user", opt.get());
		return "admin/user-form";
	}

	@PostMapping("/delete")
	public String deleteUser(HttpServletRequest req,  @RequestParam Long id, @RequestParam(required = false) boolean confirm, Model model, RedirectAttributes redirectAttributes) {
		if (isAdmin(req)) return "redirect:/login";


		if (confirm) {
			// optionally delete bookings first
			System.err.println("delete confirm");
			bookingService.deleteByUserId(id);
			redirectAttributes.addFlashAttribute("successMessage", "Flight deleted successfully.");
			return "redirect:/admin/users";
		}

		if (bookingService.existsByUserId(id)) {
			model.addAttribute("userId", id);
			model.addAttribute("user", bookingService.findById(id).orElseThrow());
			model.addAttribute("hasBookings", true);
			return "admin/confirmFlightDelete";
		} else {
			userService.delete(id);
			redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
			return "redirect:/admin/users";
		}
	}
}