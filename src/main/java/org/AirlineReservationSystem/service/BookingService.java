package org.AirlineReservationSystem.service;

import jakarta.transaction.Transactional;
import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.Schedule;
import org.AirlineReservationSystem.model.UserAccount;
import org.AirlineReservationSystem.repository.BookingRepository;
import org.AirlineReservationSystem.repository.ScheduleRepository;
import org.AirlineReservationSystem.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

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
    public Booking bookSeat(Long userId, Long scheduleId, Integer seatNo) {
        Schedule schedule = scheduleRepo.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
        if (bookingRepo.countByScheduleIdAndSeatNumber(scheduleId, seatNo) > 0) {
            throw new IllegalStateException("Seat already booked");
        }
        UserAccount user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        BigDecimal base = schedule.getBasePrice();
        BigDecimal discount = BigDecimal.valueOf(user.getTier().getDiscountPercent())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal finalPrice = base.multiply(BigDecimal.ONE.subtract(discount));
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setSeatNumber(seatNo);
        booking.setPricePaid(finalPrice);
        booking.setBookedAt(LocalDateTime.now());
        return bookingRepo.save(booking);
    }
}