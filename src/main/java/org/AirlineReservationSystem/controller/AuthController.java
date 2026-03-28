package org.airlinereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.airlinereservationsystem.model.User;
import org.airlinereservationsystem.service.UserService;
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

	// Show login page
	@GetMapping("/login")
	public String loginPage(@RequestParam(required = false) String error, HttpServletRequest req, Model model) {
		HttpSession s = req.getSession(true);
		if (s != null && s.getAttribute("userId") != null) {
			return "redirect:/";
		}
		if (error != null) model.addAttribute("error", error);
		return "login";
	}

	@PostMapping("/login")
	public String doLogin(@RequestParam String username, @RequestParam String password, HttpServletRequest req, Model model) {

		var opt = userService.findByUsername(username);
		if (opt.isEmpty()) {
			model.addAttribute("error", "Invalid username or password");
			return "login";
		}
		User user = opt.get();
		if (!userService.passwordMatches(user, password)) {
			model.addAttribute("error", "Invalid username or password");
			return "login";
		}

		HttpSession s = req.getSession(true);
		s.setAttribute("userId", user.getId());
		s.setAttribute("username", user.getUsername());
		s.setAttribute("role", user.getRole().name());

		String saved = (String) s.getAttribute("redirectAfterLogin");
		if (saved != null) {
			s.removeAttribute("redirectAfterLogin");
			return "redirect:" + saved;
		}

		if ("ADMIN".equalsIgnoreCase(user.getRole().name())) return "redirect:/admin/dashboard";
		return "redirect:/user/home";
	}

	@GetMapping("/register")
	public String registerPage(HttpServletRequest req, Model model) {
		HttpSession s = req.getSession(true);
		if (s != null && s.getAttribute("userId") != null) {
			return "redirect:/";
		}
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result, HttpServletRequest req, Model model) {

		if (result.hasErrors()) return "register";

		try {
			userService.register(user);
		} catch (IllegalArgumentException e) {

			String msg = e.getMessage();

			if (msg.contains("Username")) {
				result.rejectValue("username", "error.user", msg);
			} else if (msg.contains("Email")) {
				result.rejectValue("email", "error.user", msg);
			} else {
				result.reject("error.user", msg);
			}

			return "register";
		}

		// login logic
		HttpSession session = req.getSession(true);
		session.setAttribute("userId", user.getId());
		session.setAttribute("username", user.getUsername());
		session.setAttribute("role", user.getRole().name());

		return "redirect:/user/home";
	}
}
