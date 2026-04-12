package org.AirlineReservationSystem.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class IfAdmin {
	private IfAdmin() {
		/* This utility class should not be instantiated */
	}

	public static boolean isNotAdmin(HttpServletRequest req) {
		HttpSession s = req.getSession(false);
		return s == null || !"ADMIN".equalsIgnoreCase(String.valueOf(s.getAttribute("role")));
	}
}
