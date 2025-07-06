package org.AirlinesReservationSystem;

abstract class Ticket {
	private static int nextTicketId = 1;
	private final int ticketId;
	private final Passenger passenger;
	private final Flight flight;
	private final double price;
	private final SeatClass seatClass;
	private BookingStatus status;

	public Ticket(Passenger passenger, Flight flight, SeatClass seatClass, double price) {
		this.ticketId = nextTicketId++;
		this.passenger = passenger;
		this.flight = flight;
		this.seatClass = seatClass;
		this.price = price;
		this.status = BookingStatus.ACTIVE;
	}

	public int getTicketId() {
		return ticketId;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public Flight getFlight() {
		return flight;
	}

	public double getPrice() {
		return price;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public abstract void displayDetails();
}
