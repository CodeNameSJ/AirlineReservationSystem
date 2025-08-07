package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.model.Schedule;
import org.AirlineReservationSystem.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepo;

    public ScheduleService(ScheduleRepository scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepo.findAll();
    }

    public void save(Schedule s) {
        scheduleRepo.save(s);
    }
}
