package org.AirlineReservationSystem.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle custom validation errors
	@ExceptionHandler(UserValidationException.class)
	public String handleUserValidation(UserValidationException ex, Model model, HttpServletRequest request) {

		// Preserve form data if needed
		Object user = request.getAttribute("user");
		if (user != null) {
			model.addAttribute("user", user);
		}

		// Attach field-specific error
		if (ex.getField() != null) {
			model.addAttribute(ex.getField() + "Error", ex.getMessage());
		} else {
			model.addAttribute("error", ex.getMessage());
		}

		return resolveView(request);
	}

	// Fallback for unexpected errors
	@ExceptionHandler(Exception.class)
	public String handleGeneric(Exception ex, Model model) {
		model.addAttribute("error", ErrorConstants.GENERIC_ERROR.getMessage());
		return "error"; // create error.html if not exists
	}

	private String resolveView(HttpServletRequest request) {
		String uri = request.getRequestURI();

		if (uri.contains("/register")) return "register";
		if (uri.contains("/login")) return "login";

		return "error";
	}
}