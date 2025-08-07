package org.AirlineReservationSystem.model;

import lombok.Getter;

@Getter
public enum UserTier {
    SILVER(0), FREQUENT(5), GOLD(9), PLATINUM(12);

    private final int discountPercent;

    UserTier(int discountPercent) {
        this.discountPercent = discountPercent;
    }
}