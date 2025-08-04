package org.AirlineReservationSystem.service;

import jakarta.transaction.Transactional;
import org.AirlineReservationSystem.dto.BookingRequest;
import org.AirlineReservationSystem.dto.BookingResponse;
import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.repository.BookingRepository;
import org.AirlineReservationSystem.repository.ScheduleRepository;
import org.AirlineReservationSystem.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class BookingService {
    private final BookingRepository bookingRepo;
    private final ScheduleRepository scheduleRepo;
    private final UserAccountRepository userRepo;

    public BookingService(BookingRepository bookingRepo, ScheduleRepository scheduleRepo, UserAccountRepository userRepo) {
        this.bookingRepo = bookingRepo;
        this.scheduleRepo = scheduleRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public BookingResponse bookSeat(BookingRequest req) {
        var schedule = scheduleRepo.findById(req.getScheduleId()).orElseThrow(() -> new NoSuchElementException("Schedule not found"));
        if (bookingRepo.countByScheduleIdAndSeatNumber(req.getScheduleId(), req.getSeatNumber()) > 0)
            throw new IllegalStateException("Seat already booked");
        var user = userRepo.findById(req.getUserId()).orElseThrow(() -> new NoSuchElementException("User not found"));
        BigDecimal discount = BigDecimal.valueOf(user.getTier().getDiscountPercent()).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP);
        BigDecimal price = schedule.getBasePrice().multiply(BigDecimal.ONE.subtract(discount));
        var booking = new Booking();
        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setSeatNumber(req.getSeatNumber());
        booking.setPricePaid(price);
        booking.setBookedAt(LocalDateTime.now());
        var saved = bookingRepo.save(booking);
        return new BookingResponse(saved.getId(), saved.getSeatNumber(), saved.getPricePaid(), saved.getBookedAt());
    }
}