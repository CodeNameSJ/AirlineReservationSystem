package org.AirlineReservationSystem.service;

import jakarta.transaction.Transactional;
import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.repository.BookingRepository;
import org.AirlineReservationSystem.repository.FlightRepository;
import org.AirlineReservationSystem.repository.ScheduleRepository;
import org.AirlineReservationSystem.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final BookingRepository bookingRepo;
    private final FlightRepository flightRepo;
    private final UserRepository userRepo;

    public BookingService(BookingRepository bookingRepo, FlightRepository flightRepo, UserRepository userRepo) {
        this.bookingRepo = bookingRepo;
        this.flightRepo = flightRepo;
        this.userRepo = userRepo;
    }

//    @Transactional
//    public BookingResponse bookSeat(BookingRequest req) {
//        var schedule = flightRepo.findById(req.getScheduleId()).orElseThrow(() -> new NoSuchElementException("Schedule not found"));
//        if (bookingRepo.countByScheduleIdAndSeatNumber(req.getScheduleId(), req.getSeatNumber()) > 0)
//            throw new IllegalStateException("Seat already booked");
//        var user = userRepo.findById(req.getUserId()).orElseThrow(() -> new NoSuchElementException("User not found"));
//        BigDecimal discount = BigDecimal.valueOf(user.getUserTier().getDiscountPercent()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
//        BigDecimal price = schedule.getBasePrice().multiply(BigDecimal.ONE.subtract(discount));
//        var booking = new Booking();
//        booking.setUser(user);
//        booking.setSchedule(schedule);
//        booking.setSeatNumber(req.getSeatNumber());
//        booking.setPricePaid(price);
//        booking.setBookedAt(LocalDateTime.now());
//        var saved = bookingRepo.save(booking);
//        return new BookingResponse(saved.getId(), saved.getSeatNumber(), saved.getPricePaid(), saved.getBookedAt());
//    }

    @Transactional
    public Booking makeBooking(Long userId, Long flightId, int seats) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Flight flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found"));

        int booked = bookingRepo.totalSeatsBookedByFlight(flight);
        if (booked + seats > flight.getCapacity()) {
            throw new IllegalStateException("Not enough seats available");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setSeatsBooked(seats);

        return bookingRepo.save(booking);
    }
}