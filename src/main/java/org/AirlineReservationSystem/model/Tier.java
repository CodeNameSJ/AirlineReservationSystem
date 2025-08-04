package org.AirlineReservationSystem.model;
public enum Tier {
    SILVER(0),
    FREQUENT(5),
    GOLD(9),
    PLATINUM(12);

    private final int discountPercent;

    Tier(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }
}