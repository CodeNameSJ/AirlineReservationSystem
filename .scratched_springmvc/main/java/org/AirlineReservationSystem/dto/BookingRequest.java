package org.AirlineReservationSystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private Long userId;
    private Long scheduleId;
    private Integer seatNumber;
}