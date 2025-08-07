package org.AirlineReservationSystem.service;

import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.BookingStatus;
import org.AirlineReservationSystem.model.SeatClass;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {
	private final BookingRepository bookingRepo;
	private final FlightService flightService;

	public List<Booking> findAll() {
		return bookingRepo.findAll();
	}

	public List<Booking> findByUser(User user) {
		return bookingRepo.findByUser(user);
	}

	@Transactional
	public Booking createBooking(User user, Long flightId, SeatClass seatClass, int seats) {
		var flightOpt = flightService.findById(flightId);
		if (flightOpt.isEmpty()) throw new IllegalArgumentException("Flight not found");
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

		// save booking
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setFlight(flight);
		booking.setSeatClass(seatClass);
		booking.setSeats(seats);
		booking.setBookingTime(LocalDateTime.now());
		booking.setStatus(BookingStatus.BOOKED);
		return bookingRepo.save(booking);
	}

	@Transactional
	public void cancelBooking(Long bookingId) {
		Booking booking = bookingRepo.findById(bookingId).orElseThrow();
		if (booking.getStatus() == BookingStatus.CANCELLED)
			throw new IllegalStateException("Already cancelled");

		// revert seats
		int econDelta = booking.getSeatClass() == SeatClass.ECONOMY ? booking.getSeats() : 0;
		int busDelta = booking.getSeatClass() == SeatClass.BUSINESS ? booking.getSeats() : 0;
		flightService.updateAvailability(booking.getFlight().getId(), econDelta, busDelta);

		// update status
		booking.setStatus(BookingStatus.CANCELLED);
		bookingRepo.save(booking);
	}
}