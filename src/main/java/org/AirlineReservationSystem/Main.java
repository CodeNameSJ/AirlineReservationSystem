package org.AirlineReservationSystem;

import java.util.Scanner;

/*
 * Airlines Reservation System
 * Project by Shubham Jamdade
 * AdminId - admin, Pass: 1234
 */

public class Main {
	public static final Scanner scanner = new Scanner(System.in);
	static final BookingSystem bookingSystem = new BookingSystem();
	static final Admin admin = new Admin("admin", "1234");
	static String name;
	static String contact;
	static char choice;

	public static void main(String[] ignoredArgs) {
		try (scanner) {
			main();
		}
	}

	static void exiting(String text) {
		for (int i = 0; i < text.length(); i++) {
			System.out.print(text.charAt(i));
			try {
				Thread.sleep(124);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main() {
		do {
			System.out.println("\n╔═════ Airline Reservation ═════╗");
			System.out.println("║ \t\t 1. Admin Login         ║");
			System.out.println("║ \t\t 2. Book Ticket         ║");
			System.out.println("║ \t\t 3. View Booking        ║");
			System.out.println("║ \t\t 4. Cancel Ticket       ║");
			System.out.println("║ \t\t 0. Exit                ║");
			System.out.println("╚═══════════════════════════════╝");
			System.out.print("Choose an option: ");
			choice = scanner.next().charAt(0);
			System.out.println();
			switch (choice) {
				case '1' -> admin.adminPortal(bookingSystem);

				case '2' -> {
					scanner.nextLine();
					bookingSystem.showAllFlights();
					System.out.println();
					System.out.println("╔════════ Book a Ticket ════════╗");
					System.out.print("Enter Passenger Name: ");
					name = scanner.nextLine();
					System.out.print("Enter Contact Info: ");
					contact = scanner.nextLine();
					Passenger p = new Passenger(name, contact);
					bookingSystem.book(p);
				}

				case '3' -> {
					scanner.nextLine();
					System.out.print("Enter Passenger Name: ");
					name = scanner.nextLine();
					System.out.print("Enter Contact Info: ");
					contact = scanner.nextLine();
					bookingSystem.viewBookingsForPassenger(name, contact);
				}

				case '4' -> bookingSystem.cancel(null);

				case '0' -> exiting("Exiting...");

				default -> System.out.println("🚫 Invalid choice, please try again.");
			}
		} while (choice != '0');
	}
}