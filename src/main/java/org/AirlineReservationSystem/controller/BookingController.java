package org.AirlineReservationSystem.controller;

import org.AirlineReservationSystem.dto.BookingRequest;
import org.AirlineReservationSystem.dto.BookingResponse;
import org.AirlineReservationSystem.dto.ScheduleDTO;
import org.AirlineReservationSystem.dto.SearchRequest;
import org.AirlineReservationSystem.service.BookingService;
import org.AirlineReservationSystem.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Controller public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking/confirm")
    public String confirmBooking(@ModelAttribute BookingRequest req, Model model) {
        BookingResponse resp = bookingService.bookSeat(req);
        model.addAttribute("booking", resp);
        return "bookingConfirmation";
    }
}