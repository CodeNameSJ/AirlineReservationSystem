package org.airlinereservationsystem.service;

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
		var userOpt = userRepo.findById(userId);
		var flightOpt = flightRepo.findById(flightId);

		if (userOpt.isEmpty()) throw new IllegalArgumentException("User not found: " + userId);
		if (flightOpt.isEmpty()) throw new IllegalArgumentException("Flight not found: " + flightId);

		var flight = flightOpt.get();

		validateSeatsRequested(seats);
		ensureSeatsAvailable(flight, seatClass, seats);
		reserveSeats(flightId, seatClass, seats);

		Booking booking = new Booking();
		booking.setUser(userOpt.get());
		booking.setFlight(flight);
		booking.setSeatClass(seatClass);
		booking.setSeats(seats);
		booking.setBookingTime(LocalDateTime.now());
		booking.setStatus(BookingStatus.BOOKED);
		booking.setTotalAmount(pricingService.calculateTotal(booking));

		return bookingRepo.save(booking);
	}

	@Transactional
	public Booking saveBooking(Long id, Long userId, Long flightId, SeatClass seatClass, int seats, BookingStatus status) {
		validateSeatsRequested(seats);
		BookingStatus targetStatus = status == null ? BookingStatus.BOOKED : status;
		if (id == null) {
			Booking booking = createBooking(userId, flightId, seatClass, seats);
			if (targetStatus == BookingStatus.CANCELLED) {
				cancelBooking(booking.getId());
			}
			return booking;
		}

		Booking booking = bookingRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Booking not found: " + id));
		if (booking.getStatus() == BookingStatus.BOOKED) {
			releaseSeats(booking.getFlight(), booking.getSeatClass(), booking.getSeats());
		}

		Flight updatedFlight = flightRepo.findById(flightId).orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightId));
		if (targetStatus == BookingStatus.BOOKED) {
			ensureSeatsAvailable(updatedFlight, seatClass, seats);
			reserveSeats(flightId, seatClass, seats);
		}

		booking.setUser(userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found: " + userId)));
		booking.setFlight(updatedFlight);
		booking.setSeatClass(seatClass);
		booking.setSeats(seats);
		booking.setStatus(targetStatus);
		booking.setTotalAmount(pricingService.calculateTotal(booking));
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

	@Transactional
	public void cancelBookingForUser(Long bookingId, Long userId) {
		Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));
		if (!booking.getUser().getId().equals(userId)) {
			throw new IllegalArgumentException("You cannot cancel another user's booking.");
		}
		cancelBooking(bookingId);
	}

	public boolean existsByFlightId(Long flightId) {
		return bookingRepo.existsByFlightId(flightId);
	}

	@Transactional
	public void deleteByFlightId(Long flightId) {
		new Booking().setStatus(BookingStatus.CANCELLED);
		bookingRepo.deleteByFlightId(flightId);
	}

	@Transactional
	public void delete(Long bookingId) {
		var opt = bookingRepo.findById(bookingId);
		if (opt.isEmpty()) return;

		Booking booking = opt.get();
		if (booking.getStatus() == BookingStatus.BOOKED) {
			releaseSeats(booking.getFlight(), booking.getSeatClass(), booking.getSeats());
		}
		bookingRepo.delete(booking);
	}

	@Transactional
	public Booking save(Booking b) {
		return bookingRepo.save(b);
	}

	@Transactional
	public void deleteByUserId(Long userId) {
		bookingRepo.findByUserId(userId).forEach(booking -> {
			if (booking.getStatus() == BookingStatus.BOOKED) {
				releaseSeats(booking.getFlight(), booking.getSeatClass(), booking.getSeats());
			}
		});
		bookingRepo.deleteByUserId(userId);
	}

	public boolean existsByUserId(Long userId) {return bookingRepo.existsByUserId(userId);}

	private void validateSeatsRequested(int seats) {
		if (seats <= 0) {
			throw new IllegalArgumentException("Seats must be greater than zero.");
		}
	}

	private void ensureSeatsAvailable(Flight flight, SeatClass seatClass, int seats) {
		if (seatClass == SeatClass.ECONOMY && flight.getEconomySeatsAvailable() < seats) {
			throw new IllegalArgumentException("Not enough economy seats");
		}
		if (seatClass == SeatClass.BUSINESS && flight.getBusinessSeatsAvailable() < seats) {
			throw new IllegalArgumentException("Not enough business seats");
		}
	}

	private void reserveSeats(Long flightId, SeatClass seatClass, int seats) {
		int economyDelta = seatClass == SeatClass.ECONOMY ? -seats : 0;
		int businessDelta = seatClass == SeatClass.BUSINESS ? -seats : 0;
		flightService.updateAvailability(flightId, economyDelta, businessDelta);
	}

	private void releaseSeats(Flight flight, SeatClass seatClass, int seats) {
		if (seatClass == SeatClass.ECONOMY) {
			flight.setEconomySeatsAvailable(flight.getEconomySeatsAvailable() + seats);
		} else {
			flight.setBusinessSeatsAvailable(flight.getBusinessSeatsAvailable() + seats);
		}
		flightRepo.save(flight);
	}
}
