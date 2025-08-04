package org.AirlineReservationSystem.service;

import jakarta.transaction.Transactional;
import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.Schedule;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.repository.BookingRepository;
import org.AirlineReservationSystem.repository.ScheduleRepository;
import org.AirlineReservationSystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            throw new SeatAlreadyBookedException();
        User user = userRepo.findById(userId).orElseThrow();
        BigDecimal discount = user.getTier().getDiscountPercent();
        BigDecimal price = schedule.getBasePrice()
                .multiply(BigDecimal.valueOf(1).subtract(discount.divide(BigDecimal.valueOf(100))));
        Booking b = new Booking(user, schedule, seatNo, price, LocalDateTime.now());
        return bookingRepo.save(b);
    }
}