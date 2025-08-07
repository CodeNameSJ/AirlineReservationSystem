package org.AirlineReservationSystem.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleAll(Model model, Exception ex) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}