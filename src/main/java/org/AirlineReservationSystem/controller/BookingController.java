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

    @PostMapping("/confirm")
    public String confirmBooking(@ModelAttribute BookingRequest req, Model model) {
        BookingResponse resp = bookingService.bookSeat(req);
        model.addAttribute("booking", resp);
        return "bookingConfirmation";
    }
}