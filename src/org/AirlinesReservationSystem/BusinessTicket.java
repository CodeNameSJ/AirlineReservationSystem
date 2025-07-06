package org.AirlinesReservationSystem;

class BusinessTicket extends Ticket {
	public BusinessTicket(Passenger passenger, Flight flight) {
		super(passenger, flight, SeatClass.BUSINESS, flight.getBaseFare() * 1.5);
	}

	@Override
	public void displayDetails() {
		System.out.println("╔═════════════════════════════ Business Ticket ══════════════════════════════╗");
		System.out.println(" Ticket ID   : " + getTicketId());
		System.out.println(" Passenger   : " + getPassenger());
		System.out.println(" Flight      : " + getFlight());
		System.out.println(" Seat Class  : " + getSeatClass());
		System.out.println(" Price       : ₹" + getPrice());
		System.out.println(" Status      : " + getStatus());
		System.out.println(" Perks       : Complimentary lounge access, extra baggage");
		System.out.println("╚════════════════════════════════════════════════════════════════════════════╝\n");
	}
}
