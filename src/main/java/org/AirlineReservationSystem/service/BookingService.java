package org.AirlineReservationSystem.service;

import java.util.List;
import java.util.Optional;
import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.model.enums.BookingStatus;
import org.AirlineReservationSystem.model.enums.SeatClass;
import org.AirlineReservationSystem.repository.BookingRepository;
import org.AirlineReservationSystem.repository.FlightRepository;
import org.AirlineReservationSystem.repository.UserRepository;
import org.AirlineReservationSystem.util.ErrorConstants;
import org.AirlineReservationSystem.util.SeatReleaseProjection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

  private final BookingRepository bookingRepo;
  private final UserRepository userRepo;
  private final FlightRepository flightRepo;
  private final FlightService flightService;
  private final PricingService pricingService;

  public BookingService(
      BookingRepository bookingRepo,
      UserRepository userRepo,
      FlightRepository flightRepo,
      FlightService flightService,
      PricingService pricingService) {
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

    if (seatClass == null) {
      throw new IllegalArgumentException("Seat class required");
    }

    User user =
        userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

    Flight flight =
        flightRepo
            .findById(flightId)
            .orElseThrow(() -> new IllegalArgumentException("Flight not found"));

    flightService.reserveSeatsAtomic(flightId, seatClass, seats);

    Booking booking = new Booking();
    booking.setUser(user);
    booking.setFlight(flight);
    booking.setSeatClass(seatClass);
    booking.setSeats(seats);
    booking.setStatus(BookingStatus.BOOKED);
    booking.setTotalAmount(pricingService.calculateTotal(booking));

    return bookingRepo.save(booking);
  }

  @Transactional
  public Booking updateBooking(
      Long id, Long userId, Long flightId, SeatClass seatClass, int seats, BookingStatus status) {

    validateSeats(seats);

    if (seatClass == null) {
      throw new IllegalArgumentException();
    }

    Booking booking =
        bookingRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        ErrorConstants.NO_BOOKING_FOUND_ERROR.getMessage()));

    User user =
        userRepo
            .findById(userId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(ErrorConstants.NO_USER_FOUND_ERROR.getMessage()));

    Flight newFlight =
        flightRepo
            .findById(flightId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        ErrorConstants.NO_FLIGHT_FOUND_ERROR.getMessage()));

    // old state
    Long oldFlightId = booking.getFlight().getId();
    SeatClass oldSeatClass = booking.getSeatClass();
    int oldSeats = booking.getSeats();

    boolean wasBooked = booking.getStatus() == BookingStatus.BOOKED;
    boolean willBeBooked =
        (status == BookingStatus.BOOKED)
            || (status == null && booking.getStatus() == BookingStatus.BOOKED);

    boolean unchanged =
        oldFlightId.equals(flightId) && oldSeatClass == seatClass && oldSeats == seats;

    if (unchanged) {
      if (status != null) {
        booking.setStatus(status);
      }
      booking.setTotalAmount(pricingService.calculateTotal(booking));
      return bookingRepo.save(booking);
    }

    // release old seats
    if (wasBooked) {
      flightService.releaseSeatsAtomic(oldFlightId, oldSeatClass, oldSeats);
    }

    try {
      // reserve new seats only if needed
      if (willBeBooked) {
        flightService.reserveSeatsAtomic(flightId, seatClass, seats);
      }
    } catch (Exception e) {
      // rollback release
      if (wasBooked) {
        flightService.reserveSeatsAtomic(oldFlightId, oldSeatClass, oldSeats);
      }
      throw e;
    }

    // update entity
    booking.setUser(user);
    booking.setFlight(newFlight);
    booking.setSeatClass(seatClass);
    booking.setSeats(seats);

    if (status != null) {
      booking.setStatus(status);
    }

    booking.setTotalAmount(pricingService.calculateTotal(booking));

    return bookingRepo.save(booking);
  }

  @Transactional
  public void cancelBooking(Long bookingId) {

    Booking booking =
        bookingRepo
            .findById(bookingId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        ErrorConstants.NO_BOOKING_FOUND_ERROR.getMessage()));

    if (booking.getStatus() == BookingStatus.CANCELLED) return;

    flightService.releaseSeatsAtomic(
        booking.getFlight().getId(), booking.getSeatClass(), booking.getSeats());

    booking.setStatus(BookingStatus.CANCELLED);
    bookingRepo.save(booking);
  }

  @Transactional
  public void cancelBookingForUser(Long bookingId, Long userId) {
    Booking booking =
        bookingRepo
            .findById(bookingId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        ErrorConstants.NO_BOOKING_FOUND_ERROR.getMessage()));

    if (!booking.getUser().getId().equals(userId)) {
      throw new IllegalArgumentException(
          ErrorConstants.UNAUTHORIZED_CANCEL_ATTEMPT_ERROR.getMessage());
    }

    cancelBooking(bookingId);
  }

  @Transactional
  public void delete(Long bookingId) {

    Booking booking =
        bookingRepo
            .findById(bookingId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        ErrorConstants.NO_BOOKING_FOUND_ERROR.getMessage()));

    if (booking.getStatus() == BookingStatus.BOOKED) {
      flightService.releaseSeatsAtomic(
          booking.getFlight().getId(), booking.getSeatClass(), booking.getSeats());
    }

    bookingRepo.delete(booking);
  }

  @Transactional
  public void deleteByFlightId(Long flightId) {

    List<SeatReleaseProjection> summary = bookingRepo.getSeatReleaseSummary(flightId);

    for (SeatReleaseProjection s : summary) {
      flightService.releaseSeatsAtomic(s.getFlightId(), s.getSeatClass(), s.getTotalSeats());
    }

    bookingRepo.deleteByFlightId(flightId);
  }

  @Transactional
  public void deleteByUserId(Long userId) {

    var summary = bookingRepo.getSeatReleaseSummaryByUser(userId);

    for (var s : summary) {
      flightService.releaseSeatsAtomic(s.getFlightId(), s.getSeatClass(), s.getTotalSeats());
    }

    bookingRepo.deleteByUserId(userId);
  }

  public boolean existsByFlightId(Long flightId) {
    return bookingRepo.existsByFlightId(flightId);
  }

  public boolean existsByUserId(Long userId) {
    return bookingRepo.existsByUserId(userId);
  }

  private void validateSeats(int seats) {
    if (seats <= 0) {
      throw new IllegalArgumentException(
          ErrorConstants.SEATS_MUST_BE_GREATER_THAN_ZERO_ERROR.getMessage());
    }
  }
}
