package org.AirlineReservationSystem.service;

import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.model.enums.BookingStatus;
import org.AirlineReservationSystem.model.enums.SeatClass;
import org.AirlineReservationSystem.repository.BookingRepository;
import org.AirlineReservationSystem.repository.FlightRepository;
import org.AirlineReservationSystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {
	private final BookingRepository bookingRepo;
	private final UserRepository userRepo;
	private final FlightRepository flightRepo;
	private final FlightService flightService;

	public List<Booking> findAll() {
		return bookingRepo.findAll();
	}

	public List<Booking> findByUserId(Long userId) {
		return bookingRepo.findByUserId(userId);
	}

	public Optional<Booking> findById(Long id) {
		return bookingRepo.findById(id);
	}

	@Transactional
	public Booking createBooking(Long userId, Long flightId, SeatClass seatClass, int seats) {
		// load user and flight via repositories/services
		var userOpt = userRepo.findById(userId);
		var flightOpt = flightRepo.findById(flightId);

		if (userOpt.isEmpty()) throw new IllegalArgumentException("User not found: " + userId);
		if (flightOpt.isEmpty()) throw new IllegalArgumentException("Flight not found: " + flightId);

		var flight = flightOpt.get();

		// check availability
		if (seatClass == SeatClass.ECONOMY && flight.getEconomySeatsAvailable() < seats)
			throw new IllegalArgumentException("Not enough economy seats");
		if (seatClass == SeatClass.BUSINESS && flight.getBusinessSeatsAvailable() < seats)
			throw new IllegalArgumentException("Not enough business seats");

		// adjust availability
		int econDelta = seatClass == SeatClass.ECONOMY ? -seats : 0;
		int busDelta = seatClass == SeatClass.BUSINESS ? -seats : 0;
		flightService.updateAvailability(flightId, econDelta, busDelta);
		flightRepo.save(flight);

		Booking booking = new Booking();
		booking.setUser(userOpt.get());                 // ← IMPORTANT: set user
		booking.setFlight(flight);
		booking.setSeatClass(seatClass);
		booking.setSeats(seats);
		booking.setBookingTime(LocalDateTime.now());
		booking.setStatus(BookingStatus.BOOKED);

		return bookingRepo.save(booking);
	}

	@Transactional
	public void cancelBooking(Long bookingId) {
		var opt = bookingRepo.findById(bookingId);

		if (opt.isEmpty()) return;

		Booking booking = opt.get();

		if (booking.getStatus() == BookingStatus.CANCELLED) return;

		Flight flight = booking.getFlight();
		// restore seats
		if (booking.getSeatClass() == SeatClass.ECONOMY) {
			flight.setEconomySeatsAvailable(flight.getEconomySeatsAvailable() + booking.getSeats());
		} else {
			flight.setBusinessSeatsAvailable(flight.getBusinessSeatsAvailable() + booking.getSeats());
		}
		flightRepo.save(flight);
		// update status
		booking.setStatus(BookingStatus.CANCELLED);
		bookingRepo.save(booking);
	}

}