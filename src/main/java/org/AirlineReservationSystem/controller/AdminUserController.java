package org.airlinereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.airlinereservationsystem.model.User;
import org.airlinereservationsystem.model.enums.Role;
import org.airlinereservationsystem.service.BookingService;
import org.airlinereservationsystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.airlinereservationsystem.util.ifAdmin.isNotAdmin;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

	private final UserService userService;
	private final BookingService bookingService;

	public AdminUserController(UserService userService, BookingService bookingService) {
		this.userService = userService;
		this.bookingService = bookingService;
	}

	@GetMapping
	public String listUsers(HttpServletRequest req, Model model) {
		if (isNotAdmin(req))
			return "redirect:/login";
		model.addAttribute("users", userService.findAll());
		return "admin/users";
	}

	@GetMapping("/add")
	public String showAddForm(HttpServletRequest req, Model model) {
		if (isNotAdmin(req))
			return "redirect:/login";
		model.addAttribute("user", new User());
		model.addAttribute("roles", Role.values());
		return "admin/user-form";
	}

	@PostMapping("/save")
	public String saveUser(HttpServletRequest req, @ModelAttribute User user) {
		if (isNotAdmin(req))
			return "redirect:/login";
		if (user.getId() == null) {
			userService.register(user); // new user
		} else {
			userService.update(user); // existing user
		}
		return "redirect:/admin/users";
	}

	@GetMapping("/edit/{username}")
	public String showEditForm(HttpServletRequest req, @PathVariable String username, Model model) {
		if (isNotAdmin(req))
			return "redirect:/login";
		var opt = userService.findByUsername(username);
		if (opt.isEmpty())
			return "redirect:/admin/users";
		model.addAttribute("user", opt.get());
		model.addAttribute("roles", Role.values());
		return "admin/user-form";
	}

	@PostMapping("/delete")
	public String deleteUser(HttpServletRequest req, @RequestParam Long id,
			@RequestParam(required = false) boolean confirm, RedirectAttributes redirectAttributes) {
		if (isNotAdmin(req))
			return "redirect:/login";
		if (confirm) {
			bookingService.deleteByUserId(id);
			userService.delete(id);
			redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
		}
		return "redirect:/admin/users";
	}
}
