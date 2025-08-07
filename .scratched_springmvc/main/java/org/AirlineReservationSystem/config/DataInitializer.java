package org.AirlineReservationSystem.config;

import org.AirlineReservationSystem.model.Role;
import org.AirlineReservationSystem.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepo;

    public DataInitializer(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void run(String... args) {
        if (roleRepo.findByName("USER").isEmpty()) {
            roleRepo.save(new Role(null, "USER"));
        }
        if (roleRepo.findByName("ADMIN").isEmpty()) {
            roleRepo.save(new Role(null, "ADMIN"));
        }
    }
}