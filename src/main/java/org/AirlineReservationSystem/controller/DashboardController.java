package org.AirlineReservationSystem.controller;


import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.security.CustomUserDetails;
import org.AirlineReservationSystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final BookingService bookingService;

    @Autowired
    public DashboardController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails,
                            Model model) {
        List<Booking> bookings = bookingService.getUserBookings(userDetails.getId());

        double totalPaid = bookings.stream()
                .mapToDouble(b -> b.getSeatsBooked() * b.getFlight().getPrice())
                .sum();

        model.addAttribute("bookings", bookings);
        model.addAttribute("totalPaid", totalPaid);
        return "dashboard";
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable("id") Long bookingId,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        bookingService.cancelBooking(bookingId, userDetails.getId());
        return "redirect:/dashboard";
    }
}
