package org.airlinereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.airlinereservationsystem.model.User;
import org.airlinereservationsystem.model.enums.Role;
import org.airlinereservationsystem.model.enums.SeatClass;
import org.airlinereservationsystem.service.BookingService;
import org.airlinereservationsystem.service.UserService;
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
		model.addAttribute("roles", Role.values());
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
		model.addAttribute("roles", Role.values());
		return "admin/user-form";
	}

	@PostMapping("/delete")
	public String deleteUser(HttpServletRequest req, @RequestParam Long id, @RequestParam(required = false) boolean confirm, RedirectAttributes redirectAttributes) {
		if (isAdmin(req)) return "redirect:/login";

		if (confirm) {
			bookingService.deleteByUserId(id);
			userService.delete(id);
			redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
		}
		return "redirect:/admin/users";
	}
}