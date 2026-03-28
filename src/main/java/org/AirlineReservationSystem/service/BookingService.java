package org.airlinereservationsystem.service;

import org.airlinereservationsystem.model.Booking;
import org.airlinereservationsystem.model.Flight;
import org.airlinereservationsystem.model.User;
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
public class BookingService {

	private final BookingRepository bookingRepo;
	private final UserRepository userRepo;
	private final FlightRepository flightRepo;
	private final FlightService flightService;
	private final PricingService pricingService;

	public BookingService(BookingRepository bookingRepo, UserRepository userRepo, FlightRepository flightRepo, FlightService flightService, PricingService pricingService) {
		this.bookingRepo = bookingRepo;
		this.userRepo = userRepo;
		this.flightRepo = flightRepo;
		this.flightService = flightService;
		this.pricingService = pricingService;
	}

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

		validateSeats(seats);
		flightService.reserveSeatsAtomic(flightId, seatClass, seats);

		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

		Flight flight = flightRepo.findById(flightId).orElseThrow(() -> new IllegalArgumentException("Flight not found"));

		flightService.reserveSeatsAtomic(flightId, seatClass, seats);
		
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setFlight(flight);
		booking.setSeatClass(seatClass);
		booking.setSeats(seats);
		booking.setBookingTime(LocalDateTime.now());
		booking.setStatus(BookingStatus.BOOKED);
		booking.setTotalAmount(pricingService.calculateTotal(booking));

		return bookingRepo.save(booking);
	}

	@Transactional
	public void saveBooking(Long id, Long userId, Long flightId, SeatClass seatClass, int seats, BookingStatus status) {

		validateSeats(seats);

		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Flight flight = flightRepo.findById(flightId).orElseThrow(() -> new RuntimeException("Flight not found"));

		Booking booking;

		if (id != null) {
			booking = bookingRepo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));

			// release old seats BEFORE update
			if (booking.getStatus() == BookingStatus.BOOKED) {
				flightService.releaseSeatsAtomic(booking.getFlight().getId(), booking.getSeatClass(), booking.getSeats());
			}

		} else {
			booking = new Booking();
			booking.setBookingTime(LocalDateTime.now());
		}

		flightService.reserveSeatsAtomic(flightId, seatClass, seats);

		booking.setUser(user);
		booking.setFlight(flight);
		booking.setSeatClass(seatClass);
		booking.setSeats(seats);

		// status handling
		if (id == null) {
			booking.setStatus(status == null ? BookingStatus.BOOKED : status);
		} else if (status != null) {
			booking.setStatus(status);
		}

		booking.setTotalAmount(pricingService.calculateTotal(booking));

		bookingRepo.save(booking);
	}

	@Transactional
	public void cancelBooking(Long bookingId) {

		Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

		if (booking.getStatus() == BookingStatus.CANCELLED) return;

		flightService.releaseSeatsAtomic(booking.getFlight().getId(), booking.getSeatClass(), booking.getSeats());

		booking.setStatus(BookingStatus.CANCELLED);
		bookingRepo.save(booking);
	}

	@Transactional
	public void cancelBookingForUser(Long bookingId, Long userId) {
		Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

		if (!booking.getUser().getId().equals(userId)) {
			throw new RuntimeException("Unauthorized cancel attempt");
		}

		cancelBooking(bookingId);
	}

	@Transactional
	public void delete(Long bookingId) {

		Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

		if (booking.getStatus() == BookingStatus.BOOKED) {
			flightService.releaseSeatsAtomic(booking.getFlight().getId(), booking.getSeatClass(), booking.getSeats());
		}

		bookingRepo.delete(booking);
	}

	@Transactional
	public void deleteByFlightId(Long flightId) {

		List<Booking> bookings = bookingRepo.findByFlightId(flightId);

		for (Booking booking : bookings) {
			if (booking.getStatus() == BookingStatus.BOOKED) {
				flightService.releaseSeatsAtomic(booking.getFlight().getId(), booking.getSeatClass(), booking.getSeats());
			}
		}

		bookingRepo.deleteAll(bookings);
	}

	@Transactional
	public void deleteByUserId(Long userId) {

		List<Booking> bookings = bookingRepo.findByUserId(userId);

		for (Booking booking : bookings) {
			if (booking.getStatus() == BookingStatus.BOOKED) {
				flightService.releaseSeatsAtomic(booking.getFlight().getId(), booking.getSeatClass(), booking.getSeats());
			}
		}

		bookingRepo.deleteAll(bookings);
	}

	public boolean existsByFlightId(Long flightId) {
		return bookingRepo.existsByFlightId(flightId);
	}

	public boolean existsByUserId(Long userId) {
		return bookingRepo.existsByUserId(userId);
	}

	private void validateSeats(int seats) {
		if (seats <= 0) {
			throw new IllegalArgumentException("Seats must be greater than zero");
		}
	}

}