package org.airlinereservationsystem.service;

import org.airlinereservationsystem.model.Booking;
import org.airlinereservationsystem.model.Flight;
import org.airlinereservationsystem.model.enums.SeatClass;
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
		Flight flight = booking.getFlight();

		// Select price per seat based on class
		BigDecimal pricePerSeat = booking.getSeatClass() == SeatClass.ECONOMY
				                          ? flight.getPriceEconomy()
				                          : flight.getPriceBusiness();

		// Base subtotal
		BigDecimal subtotal = pricePerSeat.multiply(BigDecimal.valueOf(booking.getSeats()));

		// Dynamic taxes and fees
		BigDecimal tax = subtotal.multiply(taxRate);
		BigDecimal total = subtotal.add(tax).add(serviceFee);

		return total.setScale(2, RoundingMode.HALF_UP);
	}
}