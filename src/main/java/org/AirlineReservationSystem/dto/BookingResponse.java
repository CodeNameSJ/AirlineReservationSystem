package org.AirlineReservationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private Integer seatNumber;
    private BigDecimal pricePaid;
    private LocalDateTime bookedAt;
}