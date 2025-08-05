package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpSession;
import org.AirlineReservationSystem.dto.LoginDTO;
import org.AirlineReservationSystem.dto.UserRegistrationDTO;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showReg(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("registerDTO") UserRegistrationDTO dto,
                                  Model model) {
        if (!userService.registerUser(dto)) {
            model.addAttribute("registerError", "Username or Email already exists.");
            return "register";
        }
        userService.registerUser(dto);

        return "redirect:/login?registerSuccess";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("loginDTO") LoginDTO loginDTO,
                               HttpSession session,
                               Model model) {
        User user = userService.validateUser(loginDTO.getUsername(), loginDTO.getPassword());

        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/home";
        } else {
            model.addAttribute("loginError", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}