package org.AirlineReservationSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.AirlineReservationSystem.model.enums.SeatClass;
import org.AirlineReservationSystem.service.BookingService;
import org.AirlineReservationSystem.service.FlightService;
import org.AirlineReservationSystem.service.PricingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

  private final FlightService flightService;
  private final BookingService bookingService;
  private final PricingService pricingService;

  public UserController(
      FlightService flightService, BookingService bookingService, PricingService pricingService) {
    this.flightService = flightService;
    this.bookingService = bookingService;
    this.pricingService = pricingService;
  }

  private Long getUserId(HttpServletRequest req) {
    HttpSession s = req.getSession(false);
    if (s == null || s.getAttribute("userId") == null) return null;
    return (Long) s.getAttribute("userId");
  }

  @GetMapping("/home")
  public String viewBookings(HttpServletRequest req, Model model) {

    Long userId = getUserId(req);
    if (userId == null) return "redirect:/login";

    model.addAttribute("bookings", bookingService.findByUserId(userId));
    return "user/home";
  }

  @GetMapping("/book")
  public String showBookingForm(@RequestParam Long flightId, HttpServletRequest req, Model model) {

    Long userId = getUserId(req);
    if (userId == null) {
      String url = "/user/book?flightId=" + flightId;
      if (!url.startsWith("/user")) url = "/user/home";
      req.getSession(true).setAttribute("redirectAfterLogin", url);
      return "redirect:/login";
    }

    var opt = flightService.findById(flightId);
    if (opt.isEmpty()) return "redirect:/user/home";

    model.addAttribute("flight", opt.get());
    model.addAttribute("seatClasses", SeatClass.values());

    return "user/bookingForm";
  }

  @PostMapping("/book")
  public String doBook(
      @RequestParam Long flightId,
      @RequestParam int seats,
      @RequestParam SeatClass seatClass,
      HttpServletRequest req,
      Model model) {

    Long userId = getUserId(req);
    if (userId == null) {
      req.getSession(true).setAttribute("redirectAfterLogin", "/user/book?flightId=" + flightId);
      return "redirect:/login";
    }

    // basic validation
    if (seats <= 0) {
      model.addAttribute("error", "Invalid seat count");

      var opt = flightService.findById(flightId);
      opt.ifPresent(f -> model.addAttribute("flight", f));
      model.addAttribute("seatClasses", SeatClass.values());

      return "user/bookingForm";
    }

    try {
      var booking = bookingService.createBooking(userId, flightId, seatClass, seats);
      model.addAttribute("booking", booking);
      return "user/bookingSuccess";

    } catch (Exception e) {

      model.addAttribute("error", e.getMessage());

      var opt = flightService.findById(flightId);
      opt.ifPresent(f -> model.addAttribute("flight", f));
      model.addAttribute("seatClasses", SeatClass.values());

      return "user/bookingForm";
    }
  }

  @PostMapping("/bookings/cancel")
  public String cancel(
      @RequestParam Long id, HttpServletRequest req, RedirectAttributes redirectAttributes) {

    Long userId = getUserId(req);
    if (userId == null) return "redirect:/login";

    bookingService.cancelBookingForUser(id, userId);
    redirectAttributes.addFlashAttribute("successMessage", "Booking canceled successfully.");
    return "redirect:/user/home";
  }

  @GetMapping("/booking/{id}")
  public String bookingDetail(@PathVariable Long id, HttpServletRequest req, Model model) {
    String redirect = addAuthorizedBookingToModel(id, req, model);
    if (redirect != null) return redirect;
    return "user/bookingDetail";
  }

  @GetMapping("/booking/{id}/bill")
  public String bookingBill(@PathVariable Long id, HttpServletRequest req, Model model) {
    String redirect = addAuthorizedBookingToModel(id, req, model);
    if (redirect != null) return redirect;
    return "user/bookingBill";
  }

  private String addAuthorizedBookingToModel(Long id, HttpServletRequest req, Model model) {
    Long userId = getUserId(req);
    if (userId == null) return "redirect:/login";

    var opt = bookingService.findById(id);
    if (opt.isEmpty()) return "redirect:/user/home";

    var booking = opt.get();

    if (!booking.getUser().getId().equals(userId)) {
      return "redirect:/user/home";
    }

    model.addAttribute("booking", booking);
    model.addAttribute("pricing", pricingService.calculateBreakdown(booking));
    return null;
  }
}
