package org.AirlineReservationSystem.controller;

import org.AirlineReservationSystem.dto.UserRegistrationDTO;
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
    public String processRegister(@ModelAttribute("user") UserRegistrationDTO dto,
                                  Model model) {
        if (!userService.registerUser(dto)) {
            model.addAttribute("registerError", "Username or Email already exists.");
            return "register";
        }
        return "redirect:/login?registerSuccess";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}