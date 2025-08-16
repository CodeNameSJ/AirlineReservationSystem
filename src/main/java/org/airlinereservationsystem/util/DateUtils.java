package org.airlinereservationsystem.util;

import org.airlinereservationsystem.model.Flight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DateUtils {

	private static final DateTimeFormatter DISPLAY_FMT = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
	private static final DateTimeFormatter INPUT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

	private DateUtils() {}

	public static String formatForInput(LocalDateTime dt) {
		return dt == null ? "" : dt.format(INPUT_FMT);
	}

	public static String formatForDisplay(LocalDateTime dt) {
		return dt == null ? "" : dt.format(DISPLAY_FMT);
	}

	public static java.time.LocalDateTime parseFromInput(String input) {
		if (input == null) return null;
		String s = input.trim();
		if (s.isEmpty()) return null;
		return java.time.LocalDateTime.parse(s, INPUT_FMT);
	}

	/**
	 * Build and add departure/arrival formatted maps into the model.
	 * Attribute names used: "departureMap" and "arrivalMap".
	 * Usage: DateUtils.addFormattedMaps(model, flights);
	 */
	public static void addFormattedMaps(org.springframework.ui.Model model, List<Flight> flights) {
		Map<Long, String> departureMap = new HashMap<>();
		Map<Long, String> arrivalMap = new HashMap<>();
		if (flights != null) {
			for (Flight f : flights) {
				Long id = f == null ? null : f.getId();
				// guard: if id is null skip mapping
				if (id != null) {
					departureMap.put(id, formatForDisplay(f.getDepartureTime()));
					arrivalMap.put(id, formatForDisplay(f.getArrivalTime()));
				}
			}
		}
		model.addAttribute("departureMap", departureMap);
		model.addAttribute("arrivalMap", arrivalMap);
	}
}