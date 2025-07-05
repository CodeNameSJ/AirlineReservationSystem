package ARS;

class EconomyTicket extends Ticket {
	
	public EconomyTicket(Passenger passenger, Flight flight) {
		super(passenger, flight, SeatClass.ECONOMY, flight.getBaseFare());
	}
	
	@Override
	public void displayDetails() {
		System.out.println("╔═════════════════════════════ Economy Ticket ═════════════════════════════╗");
		System.out.println(" ARS.Ticket ID   : " + getTicketId());
		System.out.println(" ARS.Passenger   : " + getPassenger());
		System.out.println(" ARS.Flight      : " + getFlight());
		System.out.println(" Seat Class  : " + getSeatClass());
		System.out.println(" Price       : ₹" + getPrice());
		System.out.println(" Status      : " + getStatus());
		System.out.println("╚══════════════════════════════════════════════════════════════════════════╝\n");
	}
}