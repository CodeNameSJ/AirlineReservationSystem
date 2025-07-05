package ARS;

import static ARS.Main.scanner;

class Admin {
	
	private final String adminId;
	private String password;
	
	public Admin(String adminId, String password) {
		this.adminId = adminId;
		this.password = password;
	}
	
	public boolean login() {
		System.out.print("Admin ID: ");
		String id = scanner.next();
		System.out.print("Password: ");
		String pw = scanner.next();
		return adminId.equals(id) && password.equals(pw);
	}
	
	public void addFlightConsole(BookingSystem bookingSystem) {
		
		System.out.println("╔═════ Add New Flight ═════╗");
		System.out.print("Flight No: ");
		String no = scanner.next();
		System.out.print("Source: ");
		String src = scanner.next();
		System.out.print("Destination: ");
		String dest = scanner.next();
		System.out.print("Departure Time: ");
		String time = scanner.next();
		System.out.print("Economy Seats: ");
		int e = scanner.nextInt();
		System.out.print("Business Seats: ");
		int b = scanner.nextInt();
		System.out.print("Economy Price: ");
		double pe = scanner.nextDouble();
		System.out.print("Business Price: ");
		double pb = scanner.nextDouble();
		bookingSystem.addFlight(new Flight(no, src, dest, time, e, b, pe, pb));
		System.out.println("╚══════════════════════════╝\n");
		System.out.printf("✅ ARS.Flight no: %s from %s to %s set at %s added successfully.\n", no, src, dest, time);
	}
	
	public void viewFlights(BookingSystem bookingSystem) {
		bookingSystem.showAllFlights();
	}
	
	public void viewBookings(BookingSystem bookingSystem) {
		bookingSystem.showAllBookings();
	}
	
	public void updateFlightPrice(BookingSystem bookingSystem) {
		System.out.print("Enter Flight No to update price: ");
		String flightNo = scanner.next();
		Flight flight = bookingSystem.findFlight(flightNo);
		if (flight != null) {
			System.out.print("New Economy Price: ");
			double newEco = scanner.nextDouble();
			System.out.print("New Business Price: ");
			double newBus = scanner.nextDouble();
			flight.setPriceEconomy(newEco);
			flight.setPriceBusiness(newBus);
			System.out.println("✅ Prices updated successfully.");
		} else {
			System.out.println("🚫 Flight not found.");
		}
	}
	
	public void removeFlightConsole(BookingSystem bookingSystem) {
		bookingSystem.showAllFlights();
		System.out.println("╔═════ Remove Flight ═════╗");
		System.out.print("Enter ARS.Flight No to remove: ");
		String flightNo = scanner.next();
		boolean removed = bookingSystem.removeFlight(flightNo);
		if (removed) {
			System.out.println("✅ Flight " + flightNo + " removed successfully.");
		} else {
			System.out.println("🚫 Flight " + flightNo + " not found.");
		}
		System.out.println("╚══════════════════════════╝\n");
	}
	
	public void changePass() {
		System.out.println("Enter new Password:\n>");
		this.password = scanner.nextLine();
	}
}