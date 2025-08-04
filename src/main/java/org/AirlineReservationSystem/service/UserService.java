package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.dto.UserRegistrationDTO;
import org.AirlineReservationSystem.model.UserAccount;
import org.AirlineReservationSystem.model.UserRole;
import org.AirlineReservationSystem.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserAccountRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserAccountRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegistrationDTO dto) {
        UserAccount user = new UserAccount();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        userRepo.save(user);
    }

    public UserAccount findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }
}