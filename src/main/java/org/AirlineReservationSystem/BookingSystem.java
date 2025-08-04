package org.AirlineReservationSystem;

import static org.AirlineReservationSystem.Main.scanner;

class BookingSystem implements Bookable {
	private final Flight[] flights = new Flight[10];
	private final Ticket[] tickets = new Ticket[100];
	private int flightCount = 0;
	private int ticketCount = 0;

	BookingSystem() {
		dummyFlights();
	}

	private void dummyFlights() {
		addFlight(new Flight("AI101", "Delhi", "Mumbai", "10:00AM", 50, 10, 5000, 7200));
		addFlight(new Flight("AI102", "Pune", "Bangalore", "12:00PM", 40, 5, 3600, 5000));
		addFlight(new Flight("AI103", "Mumbai", "Chennai", "3:00PM", 30, 8, 6000, 8000));
	}


	public void addFlight(Flight f) {
		if (flightCount < flights.length) flights[flightCount++] = f;
	}

	public Flight findFlight(String flightNo) {
		for (int i = 0; i < flightCount; i++)
			if (flights[i].getFlightNo().equalsIgnoreCase(flightNo)) return flights[i];
		return null;
	}

	@Override
	public void book(Passenger passenger) {
		System.out.print("Enter Flight No: ");
		String no = scanner.next();
		Flight flight = findFlight(no);
		if (flight == null) {
			System.out.println("\n🚫 Flight not found");
			return;
		}
		System.out.print("""
				Seat Class\
				\n\t\t1.Economy\
				\n\t\t2.Business\
				\n>\s""");
		int cls = scanner.nextInt();
		SeatClass seat = (cls == 1 ? SeatClass.ECONOMY : SeatClass.BUSINESS);
		if (!flight.hasSeatAvailable(seat)) {
			System.out.println("🚫 No " + seat + " seats available!");
			return;
		}
		double price = flight.getPrice(seat);
		System.out.println("Price for this ticket: ₹" + price);
		System.out.print("Do you want to confirm booking? (Y/N): ");
		char confirm = scanner.next().toUpperCase().charAt(0);

		if (confirm != 'Y') {
			System.out.println("❌ Booking cancelled by user.");
			return;
		}
		flight.reserveSeat(seat);
		Ticket t = (seat == SeatClass.ECONOMY) ? new EconomyTicket(passenger, flight) : new BusinessTicket(passenger, flight);
		tickets[ticketCount++] = t;
		System.out.println("╚════════════════════════════════╝\n");
		System.out.println("✅ Ticket Booked Successfully!");
		System.out.println("🆔 Ticket ID: " + t.getTicketId() + " | Passenger: " + passenger + " | Seat: " + seat);
	}

	@Override
	public void cancel(Passenger passenger) {
		System.out.println("╔════════ Cancel a Ticket ════════╗");
		System.out.print("║ Enter Ticket ID to cancel: ");
		int id = scanner.nextInt();
		for (int i = 0; i < ticketCount; i++) {
			Ticket t = tickets[i];
			if (t.getTicketId() == id && t.getStatus() == BookingStatus.ACTIVE) {
				t.setStatus(BookingStatus.CANCELLED);
				t.getFlight().releaseSeat(t.getSeatClass());
				System.out.println("╚═════════════════════════════════╝\n");
				System.out.println("✅ Ticket " + id + " cancelled successfully.");
				return;
			}
		}
		System.out.println("╚═════════════════════════════════╝\n");
		System.out.println("🚫 Active ticket not found with ID " + id);
	}

	public void showAllBookings() {
		if (ticketCount == 0) {
			System.out.println("🚫 No active booking");
			return;
		}
		System.out.println("════════════════════════════════ Active Bookings ════════════════════════════════");
		for (int i = 0; i < ticketCount; i++) {
			Ticket t = tickets[i];
			if (t.getStatus() == BookingStatus.ACTIVE) t.displayDetails();
		}
		System.out.println("════════════════════════════════════════════════════════════════════════════════\n");
	}

	public void showAllFlights() {
		if (flightCount == 0) {
			System.out.println("🚫 No active flights");
			return;
		}
		System.out.println("╔═════════════ Available Flights ════════════╗");
		for (int i = 0; i < flightCount; i++) System.out.print(flights[i]);
		System.out.println("╚════════════════════════════════════════════╝\n");
	}

	public boolean removeFlight(String flightNo) {
		for (int i = 0; i < flightCount; i++) {
			if (flights[i].getFlightNo().equals(flightNo)) {
				for (int j = i; j < flightCount - 1; j++) {
					flights[j] = flights[j + 1];
				}
				flights[--flightCount] = null;
				return true;
			}
		}
		return false;
	}

	public void viewBookingsForPassenger(String name, String contact) {
		boolean found = false;

		System.out.println("\n════════════ Your Bookings ════════════");
		for (int i = 0; i < ticketCount; i++) {
			Ticket t = tickets[i];
			Passenger p = t.getPassenger();
			if (p.getName().equals(name) && p.getContact().equals(contact) && t.getStatus() == BookingStatus.ACTIVE) {
				t.displayDetails();
				found = true;
			}
		}
		if (!found) {
			System.out.println("🚫 No active bookings found for " + name);
		}
		System.out.println("══════════════════════════════════════\n");
	}
}