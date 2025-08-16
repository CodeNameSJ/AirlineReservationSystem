package org.airlinereservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.airlinereservationsystem.model.Booking;
import org.airlinereservationsystem.model.Flight;
import org.airlinereservationsystem.model.enums.BookingStatus;
import org.airlinereservationsystem.model.enums.SeatClass;
import org.airlinereservationsystem.repository.BookingRepository;
import org.airlinereservationsystem.repository.FlightRepository;
import org.airlinereservationsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
		var userOpt = userRepo.findById(userId);
		var flightOpt = flightRepo.findById(flightId);

		if (userOpt.isEmpty()) throw new IllegalArgumentException("User not found: " + userId);
		if (flightOpt.isEmpty()) throw new IllegalArgumentException("Flight not found: " + flightId);

		var flight = flightOpt.get();

		if (seatClass == SeatClass.ECONOMY && flight.getEconomySeatsAvailable() < seats)
			throw new IllegalArgumentException("Not enough economy seats");
		if (seatClass == SeatClass.BUSINESS && flight.getBusinessSeatsAvailable() < seats)
			throw new IllegalArgumentException("Not enough business seats");

		// adjust availability via flightService (should be transactional too)
		int econDelta = seatClass == SeatClass.ECONOMY ? -seats : 0;
		int busDelta = seatClass == SeatClass.BUSINESS ? -seats : 0;
		flightService.updateAvailability(flightId, econDelta, busDelta);
		flightRepo.save(flight);

		Booking booking = new Booking();
		booking.setUser(userOpt.get());
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
		if (booking.getSeatClass() == SeatClass.ECONOMY) {
			flight.setEconomySeatsAvailable(flight.getEconomySeatsAvailable() + booking.getSeats());
		} else {
			flight.setBusinessSeatsAvailable(flight.getBusinessSeatsAvailable() + booking.getSeats());
		}
		flightRepo.save(flight);

		booking.setStatus(BookingStatus.CANCELLED);
		bookingRepo.save(booking);
	}

	public boolean existsByFlightId(Long flightId) {
		return bookingRepo.existsByFlightId(flightId);
	}

	@Transactional
	public void deleteByFlightId(Long flightId) {
		bookingRepo.deleteByFlightId(flightId);
	}

	@Transactional
	public void delete(Long bookingId) {
		bookingRepo.deleteById(bookingId);
	}

	@Transactional
	public Booking save(Booking b) {
		return bookingRepo.save(b);
	}

	@Transactional
	public void deleteByUserId(Long userId) {
		bookingRepo.deleteByUserId(userId);
	}

	public boolean existsByUserId(Long userId) {return bookingRepo.existsByUserId(userId);}
}