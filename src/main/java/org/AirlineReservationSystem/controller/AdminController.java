package org.AirlineReservationSystem.controller;

import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.model.Schedule;
import org.AirlineReservationSystem.service.FlightService;
import org.AirlineReservationSystem.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FlightService flightService;
    private final ScheduleService scheduleService;

    public AdminController(FlightService flightService, ScheduleService scheduleService) {
        this.flightService = flightService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/flights")
    public String allFlights(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        return "admin/flights";
    }

    @PostMapping("/flights/add")
    public String addFlight(@ModelAttribute Flight flight) {
        flightService.save(flight);
        return "redirect:/admin/flights";
    }

    @GetMapping("/schedules")
    public String allSchedules(Model model) {
        model.addAttribute("schedules", scheduleService.getAllSchedules());
        return "admin/schedules";
    }

    @PostMapping("/schedules/add")
    public String addSchedule(@ModelAttribute Schedule schedule) {
        scheduleService.save(schedule);
        return "redirect:/admin/schedules";
    }
}
