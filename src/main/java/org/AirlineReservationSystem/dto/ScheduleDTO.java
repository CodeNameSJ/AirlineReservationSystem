package org.AirlineReservationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleDTO {
    private Long scheduleId;
    private String flightCode;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private BigDecimal basePrice;

    public ScheduleDTO(Long id, String code, String code1, LocalDateTime departure, LocalDateTime arrival, BigDecimal basePrice) {
    }
}