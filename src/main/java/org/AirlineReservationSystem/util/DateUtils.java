package org.AirlineReservationSystem.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

	public static String formatForInput(LocalDateTime dt) {
		return dt == null ? "" : dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
	}

	public static String formatForDisplay(LocalDateTime dt) {
		return dt == null ? "" : dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
	}

	public static LocalDateTime parseFromInput(String input) {
		if (input == null) return null;
		String s = input.trim();
		if (s.isEmpty()) return null;
		return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
	}
}