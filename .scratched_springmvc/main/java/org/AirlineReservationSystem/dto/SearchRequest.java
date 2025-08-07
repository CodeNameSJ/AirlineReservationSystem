package org.AirlineReservationSystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchRequest {
    private String originCode;
    private String destinationCode;
    private LocalDateTime from;
    private LocalDateTime to;
}