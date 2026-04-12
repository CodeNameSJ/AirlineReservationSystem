package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.model.enums.Role;
import org.AirlineReservationSystem.service.BookingService;
import org.AirlineReservationSystem.service.UserService;
import org.AirlineReservationSystem.util.UserValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.AirlineReservationSystem.util.IfAdmin.isNotAdmin;

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
		if (isNotAdmin(req)) return "redirect:/login";
		model.addAttribute("users", userService.findAll());
		return "admin/users";
	}

	@GetMapping("/add")
	public String showAddForm(HttpServletRequest req, Model model) {
		if (isNotAdmin(req)) return "redirect:/login";
		model.addAttribute("user", new User());
		model.addAttribute("roles", Role.values());
		return "admin/user-form";
	}

	@PostMapping("/save")
	public String saveUser(HttpServletRequest req, @ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
		if (isNotAdmin(req)) return "redirect:/login";
		try {
			if (user.getId() == null) {
				userService.register(user);
				redirectAttributes.addFlashAttribute("successMessage", "User created successfully.");
			} else {
				userService.update(user);
				redirectAttributes.addFlashAttribute("successMessage", "User updated successfully.");
			}
			return "redirect:/admin/users";
		} catch (UserValidationException e) {
			model.addAttribute("errorMessage", e.getMessage());
			model.addAttribute("user", user);
			model.addAttribute("roles", Role.values());
			return "admin/user-form";
		}
	}

	@GetMapping("/edit/{username}")
	public String showEditForm(HttpServletRequest req, @PathVariable String username, Model model) {
		if (isNotAdmin(req)) return "redirect:/login";
		var opt = userService.findByUsername(username);
		if (opt.isEmpty()) return "redirect:/admin/users";
		User user = opt.get();
		if (!user.getUsername().equals(username)) {
			return "redirect:/admin/users/edit/" + user.getUsername();
		}
		model.addAttribute("user", user);
		model.addAttribute("roles", Role.values());
		return "admin/user-form";
	}

	@PostMapping("/delete")
	public String deleteUser(HttpServletRequest req, @RequestParam Long id, @RequestParam(required = false) boolean confirm, RedirectAttributes redirectAttributes) {
		if (isNotAdmin(req)) return "redirect:/login";
		if (confirm) {
			bookingService.deleteByUserId(id);
			userService.delete(id);
			redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
		}
		return "redirect:/admin/users";
	}
}
