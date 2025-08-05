package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.dto.UserRegistrationDTO;
import org.AirlineReservationSystem.model.Role;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.model.UserTier;
import org.AirlineReservationSystem.repository.RoleRepository;
import org.AirlineReservationSystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public boolean registerUser(UserRegistrationDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        userRepo.findByEmail(dto.getEmail()).ifPresent(u ->
                { throw new IllegalArgumentException("Email already in use"); }
        );
        userRepo.findByUsername(dto.getUsername()).ifPresent(u ->
                { throw new IllegalArgumentException("Username already taken"); }
        );
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUserTier(UserTier.SILVER);
        Role role = roleRepo.findByName("USER")
                .orElseThrow(() -> new RuntimeException("USER missing"));
        user.setRole(role);

        userRepo.save(user);
        return true;
    }
}