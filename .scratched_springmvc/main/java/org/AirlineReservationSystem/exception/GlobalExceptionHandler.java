package org.AirlineReservationSystem.exception;

<<<<<<< Updated upstream
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleAll(Model model, Exception ex) {
        model.addAttribute("message", ex.getMessage());
=======
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBadRequest(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorCode", 400);
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";      // error.jsp
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleConflict(IllegalStateException ex, Model model) {
        model.addAttribute("errorCode", 409);
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorCode", 404);
        model.addAttribute("errorMessage", "Page not found: " + ex.getRequestURL());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, HttpServletRequest req, Model model) {
        model.addAttribute("errorCode", 500);
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again.");
        // log ex along with req.getRequestURI()
>>>>>>> Stashed changes
        return "error";
    }
}