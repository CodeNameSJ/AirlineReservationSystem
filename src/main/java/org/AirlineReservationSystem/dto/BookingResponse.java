package org.AirlineReservationSystem.dto;

import lombok.*;

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