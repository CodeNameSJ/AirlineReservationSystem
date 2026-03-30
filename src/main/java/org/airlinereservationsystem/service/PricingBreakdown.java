package org.airlinereservationsystem.service;

import java.math.BigDecimal;

public record PricingBreakdown(
		BigDecimal baseFare,
		BigDecimal taxAmount,
		BigDecimal serviceFee,
		BigDecimal totalAmount
) {
}
