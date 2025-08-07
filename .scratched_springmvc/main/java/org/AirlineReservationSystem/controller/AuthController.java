package org.AirlineReservationSystem.controller;

<<<<<<< Updated upstream
=======
import jakarta.validation.Valid;
>>>>>>> Stashed changes
import org.AirlineReservationSystem.dto.UserRegistrationDTO;
import org.AirlineReservationSystem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
=======
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
    public String register(@ModelAttribute UserRegistrationDTO dto) {
        userService.register(dto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
=======
    public String processRegistration(
            @ModelAttribute("user") @Valid UserRegistrationDTO userDto,
            BindingResult errors,
            RedirectAttributes redirectAttrs
    ) {
        if (errors.hasErrors()) {
            return "register";
        }
        userService.registerUser(userDto);
        redirectAttrs.addFlashAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registrationSuccess(@RequestParam(value = "success", required = false) String success,
                                      Model model) {
        if (success != null) {
            model.addAttribute("message", "Registration successful! Please log in.");
        }
        return "register";
    }

    @GetMapping("/login")
    public String showLogin(@ModelAttribute("successMessage") String successMessage, Model model) {
        // successMessage will appear once after redirect
        model.addAttribute("successMessage", successMessage);
>>>>>>> Stashed changes
        return "login";
    }
}