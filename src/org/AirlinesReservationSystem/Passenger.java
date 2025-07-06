package org.AirlinesReservationSystem;

class Passenger {
	private static int nextPassengerId = 1;
	private final int passengerId;
	private final String name;
	private final String contact;

	public Passenger(String name, String contact) {
		this.passengerId = nextPassengerId++;
		this.name = name;
		this.contact = contact;
	}

	public String getName() {
		return name;
	}

	public String getContact() {
		return contact;
	}

	@Override
	public String toString() {
		return String.format("[%d] %s (%s)", passengerId, name, contact);
	}
}