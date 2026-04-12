package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.service.UserService;
import org.AirlineReservationSystem.util.UserValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String loginPage(@RequestParam(required = false) String error, HttpServletRequest req, Model model) {

		HttpSession s = req.getSession(false);
		if (s != null && s.getAttribute("userId") != null) {
			return "redirect:/";
		}

		if (error != null) model.addAttribute("error", error);
		return "login";
	}

	@PostMapping("/login")
	public String doLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest req, Model model) {

		var opt = userService.findByUsername(username);

		if (opt.isEmpty() || !userService.passwordMatches(opt.get(), password)) {
			model.addAttribute("error", "Invalid username or password");
			return "login";
		}

		User user = opt.get();

		HttpSession session = req.getSession(true);
		session.setAttribute("userId", user.getId());
		session.setAttribute("username", user.getUsername());
		session.setAttribute("role", user.getRole().name());

		// redirect handling
		String redirect = (String) session.getAttribute("redirectAfterLogin");
		session.removeAttribute("redirectAfterLogin");

		if (redirect != null && redirect.startsWith("/")) {
			return "redirect:" + redirect;
		}

		// role-based fallback
		if ("ADMIN".equalsIgnoreCase(user.getRole().name())) {
			return "redirect:/admin/dashboard";
		}

		return "redirect:/user/home";
	}

	@GetMapping("/register")
	public String registerPage(HttpServletRequest req, Model model) {

		HttpSession s = req.getSession(false);
		if (s != null && s.getAttribute("userId") != null) {
			return "redirect:/";
		}

		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result, HttpServletRequest req) {

		if (result.hasErrors()) return "register";

		try {
			userService.register(user);
		} catch (UserValidationException e) {
			result.rejectValue(e.getField(), "error.user", e.getMessage());
			return "register";
		}

		HttpSession session = req.getSession(true);
		session.setAttribute("userId", user.getId());
		session.setAttribute("username", user.getUsername());
		session.setAttribute("role", user.getRole().name());

		return "redirect:/user/home";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession s = req.getSession(false);
		if (s != null) s.invalidate();
		return "redirect:/";
	}
}