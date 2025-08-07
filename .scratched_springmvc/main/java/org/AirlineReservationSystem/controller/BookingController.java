package org.AirlineReservationSystem.controller;

import org.AirlineReservationSystem.dto.BookingRequest;
import org.AirlineReservationSystem.dto.BookingResponse;
import org.AirlineReservationSystem.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

<<<<<<< Updated upstream
    @PostMapping("/confirm")
    public String confirmBooking(@ModelAttribute BookingRequest req, Model model) {
        BookingResponse resp = bookingService.bookSeat(req);
        model.addAttribute("booking", resp);
        return "bookingConfirmation";
=======
    @GetMapping
    public String showForm(@RequestParam("flightId") Long flightId, Model model) {
        Flight flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid flight ID"));
        model.addAttribute("flight", flight);
        model.addAttribute("bookingForm", new BookingForm());
        return "booking-form";
    }

    // DTO for binding
    public static class BookingForm {
        @NotNull @Min(value=1, message="Must book at least one seat")
        private Integer seats;
        // getters/setters
        public Integer getSeats() { return seats; }
        public void setSeats(Integer seats) { this.seats = seats; }
    }

    // Process booking
    @PostMapping
    public String processBooking(
            @RequestParam("flightId") Long flightId,
            @ModelAttribute("bookingForm") @Valid BookingForm form,
            BindingResult errors,
            @AuthenticationPrincipal CustomUserDetails principal,
            Model model
    ) {
        if (errors.hasErrors()) {
            model.addAttribute("flight", flightRepo.findById(flightId).get());
            return "booking-form";
        }
        try {
            User user = new User();
            user.setId(principal.getId());
            bookingService.makeBooking(user.getId(), flightId, form.getSeats());
        } catch (IllegalStateException ex) {
            errors.rejectValue("seats", "noSeats", ex.getMessage());
            model.addAttribute("flight", flightRepo.findById(flightId).get());
            return "booking-form";
        }

        return "redirect:/booking/confirm";
    }

    // Confirmation page
    @GetMapping("/confirm")
    public String confirm() {
        return "booking-confirm";
>>>>>>> Stashed changes
    }
}