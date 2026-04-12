package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.model.enums.SeatClass;
import org.AirlineReservationSystem.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PricingService {

	@Value("${pricing.tax-rate}")
	private BigDecimal taxRate;

	@Value("${pricing.service-fee}")
	private BigDecimal serviceFee;

	public BigDecimal calculateTotal(Booking booking) {
		return calculateBreakdown(booking).totalAmount();
	}

	public PricingBreakdown calculateBreakdown(Booking booking) {

		if (booking == null || booking.getFlight() == null || booking.getSeatClass() == null) {
			throw new IllegalArgumentException(Constants.INVALID_BOOKING_DATA_ERROR.getMessage());
		}

		Flight flight = booking.getFlight();

		BigDecimal pricePerSeat = booking.getSeatClass() == SeatClass.ECONOMY ? flight.getPriceEconomy() : flight.getPriceBusiness();

		BigDecimal subtotal = pricePerSeat.multiply(BigDecimal.valueOf(booking.getSeats()));

		BigDecimal tax = subtotal.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);

		BigDecimal total = subtotal.add(tax).add(serviceFee);

		return new PricingBreakdown(subtotal.setScale(2, RoundingMode.HALF_UP), tax, serviceFee.setScale(2, RoundingMode.HALF_UP), total.setScale(2, RoundingMode.HALF_UP));
	}
}
