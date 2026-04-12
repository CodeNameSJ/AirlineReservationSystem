package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.model.enums.SeatClass;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PricingServiceTest {

	@Test
	void calculatesDetailedBreakdownFromConfiguredRates() {
		PricingService pricingService = new PricingService();
		ReflectionTestUtils.setField(pricingService, "taxRate", new BigDecimal("0.10"));
		ReflectionTestUtils.setField(pricingService, "serviceFee", new BigDecimal("50"));

		Flight flight = new Flight();
		flight.setPriceEconomy(new BigDecimal("4500"));
		flight.setPriceBusiness(new BigDecimal("9000"));

		Booking booking = new Booking();
		booking.setFlight(flight);
		booking.setSeatClass(SeatClass.BUSINESS);
		booking.setSeats(1);

		PricingBreakdown breakdown = pricingService.calculateBreakdown(booking);

		assertThat(breakdown.baseFare()).isEqualByComparingTo("9000.00");
		assertThat(breakdown.taxAmount()).isEqualByComparingTo("900.00");
		assertThat(breakdown.serviceFee()).isEqualByComparingTo("50.00");
		assertThat(breakdown.totalAmount()).isEqualByComparingTo("9950.00");
		assertThat(pricingService.calculateTotal(booking)).isEqualByComparingTo("9950.00");
	}
}
