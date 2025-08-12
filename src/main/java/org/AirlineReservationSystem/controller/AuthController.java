package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.model.enums.Role;
import org.AirlineReservationSystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;

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
		if (!user.getPassword().equals(password)) {
			model.addAttribute("error", "Invalid username or password");
			return "login";
		}

		HttpSession s = req.getSession(true);
		s.setAttribute("userId", user.getId());
		s.setAttribute("username", user.getUsername());
		s.setAttribute("role", user.getRole());

		// redirect to saved URL if set
		String saved = (String) s.getAttribute("redirectAfterLogin");
		if (saved != null) {
			s.removeAttribute("redirectAfterLogin");
			return "redirect:" + saved;
		}

		if ("ADMIN".equalsIgnoreCase(user.getRole().name())) return "redirect:/admin/dashboard";
		return "redirect:/user/home";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession s = req.getSession(false);
		if (s != null) s.invalidate();
		return "redirect:/";
	}

	@GetMapping("/register")
	public String registerPage(HttpServletRequest req) {
		HttpSession s = req.getSession(true);
		if (s != null && s.getAttribute("userId") != null) {
			return "redirect:/";
		}
		return "register";
	}

	@PostMapping("/register")
	public String doRegister(@RequestParam String username, @RequestParam String email, @RequestParam String password, HttpServletRequest req, Model model) {
		if (userService.findByUsername(username).isPresent()) {
			model.addAttribute("error", "Username taken");
			return "register";
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setRole(Role.USER);
		userService.save(user);

		// auto-login
		HttpSession session = req.getSession(true);
		session.setAttribute("userId", user.getId());
		session.setAttribute("username", user.getUsername());
		session.setAttribute("role", user.getRole());

		return "redirect:/user/home";
	}
}