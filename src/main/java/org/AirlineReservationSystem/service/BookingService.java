package org.AirlineReservationSystem.service;

import jakarta.transaction.Transactional;
import org.AirlineReservationSystem.exception.SeatAlreadyBookedException;
import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.Schedule;
import org.AirlineReservationSystem.model.UserAccount;
import org.AirlineReservationSystem.repository.BookingRepository;
import org.AirlineReservationSystem.repository.ScheduleRepository;
import org.AirlineReservationSystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class BookingService {
    private final BookingRepository bookingRepo;
    private final ScheduleRepository scheduleRepo;
    private final UserRepository userRepo;

    public BookingService(BookingRepository bookingRepo, ScheduleRepository scheduleRepo, UserRepository userRepo) {
        this.bookingRepo = bookingRepo;
        this.scheduleRepo = scheduleRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public Booking bookSeat(Long userId, Long scheduleId, int seatNo) {
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow();
        if (bookingRepo.countByScheduleAndSeatNumber(schedule, seatNo) > 0)
            try {
                throw new SeatAlreadyBookedException();
            } catch (SeatAlreadyBookedException e) {
                throw new RuntimeException(e);
            }
        UserAccount userAccount = userRepo.findById(userId).orElseThrow();
        BigDecimal discount = BigDecimal.valueOf(userAccount.getTier().getDiscountPercent());
        BigDecimal price = schedule.getBasePrice()
                .multiply(BigDecimal.valueOf(1).subtract(discount.divide(BigDecimal.valueOf(100d), 2, RoundingMode.HALF_UP)));
        Booking b = new Booking(userAccount, schedule, seatNo, price, LocalDateTime.now());
        return bookingRepo.save(b);
    }
}