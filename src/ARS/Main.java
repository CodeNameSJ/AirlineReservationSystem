package ARS;

import java.util.Scanner;

/*
 * Airlines Reservation System
 * Project by Shubham Jamdade
 * AdminId - admin, Pass: 1234
 */

public class Main {
	public static final Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] ignoredArgs) {
		try (scanner) {
			BookingSystem bookingSystem = new BookingSystem();
			Admin admin = new Admin("admin", "1234");
			String name, contact;
			int choice;
			
			do {
				System.out.println("\n╔═════ Airline Reservation ═════╗");
				System.out.println("║ \t\t 1. Admin Login         ║");
				System.out.println("║ \t\t 2. Book Ticket         ║");
				System.out.println("║ \t\t 3. View Booking        ║");
				System.out.println("║ \t\t 4. Cancel Ticket       ║");
				System.out.println("║ \t\t 0. Exit                ║");
				System.out.println("╚═══════════════════════════════╝");
				System.out.print("Choose an option: ");
				choice = scanner.nextInt();
				System.out.println();
				switch (choice) {
					case 1:
						if (admin.login()) {
							char c;
							do {
								System.out.println("\n╔═════ Admin Portal ═════╗");
								System.out.println("║\t 1. Add Flight       ║");
								System.out.println("║\t 2. View Flights     ║");
								System.out.println("║\t 3. View Bookings    ║");
								System.out.println("║\t 4. Update Price     ║");
								System.out.println("║\t 5. Remove Flight    ║");
//							System.out.println("║\t 9. Change Admin Pass║");
								System.out.println("║\t 0. Logout           ║");
								System.out.println("╚════════════════════════╝");
								System.out.print("Choose an option: ");
								c = scanner.next().charAt(0);
								System.out.println();
								if (c == '1') admin.addFlightConsole(bookingSystem);
								else if (c == '2') admin.viewFlights(bookingSystem);
								else if (c == '3') admin.viewBookings(bookingSystem);
								else if (c == '4') admin.updateFlightPrice(bookingSystem);
								else if (c == '5') admin.removeFlightConsole(bookingSystem);
//							else if (c == '9') admin.changePass();       // password change
								else if (c == '0') {
									exiting("Logging Out...");
									break;
								}
							} while (c != 0);
						} else System.out.println("🚫 Invalid credentials");
						break;
					
					case 2:
						scanner.nextLine();
						bookingSystem.showAllFlights();
						System.out.println();
						System.out.println("╔════════ Book a Ticket ════════╗");
						System.out.print("Enter ARS.Passenger Name: ");
						name = scanner.nextLine();
						System.out.print("Enter Contact Info: ");
						contact = scanner.nextLine();
						Passenger p = new Passenger(name, contact);
						bookingSystem.book(p);
						break;
					case 3:
						scanner.nextLine(); // consume leftover newline
						System.out.print("Enter Passenger Name: ");
						name = scanner.nextLine();
						System.out.print("Enter Contact Info: ");
						contact = scanner.nextLine();
						bookingSystem.viewBookingsForPassenger(name, contact);
						break;
					case 4:
						bookingSystem.cancel(null);
						break;
					
					case 0:
						exiting("Exiting...");
						break;
					default:
						System.out.println("🚫 Invalid choice, please try again.");
				}
			} while (choice != 0);
		}
	}
	
	static void exiting(String text) {
		for (int i = 0; i < text.length(); i++) {
			System.out.print(text.charAt(i));
			try {
				Thread.sleep(120);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}