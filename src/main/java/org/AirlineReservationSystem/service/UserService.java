package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.dto.LoginDTO;
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

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    public boolean loginUser(LoginDTO dto) {
        User user = userRepo.findByUsername(dto.getUsername());
        if (user == null) return false;

        return passwordEncoder.matches(dto.getPassword(), user.getPassword());
    }

    public User validateUser(String username, String password) {
        return userRepo.findByUsernameAndPassword(username, password);
    }

    public boolean registerUser(UserRegistrationDTO dto) {
        if (userRepo.existsByUsername(dto.getUsername()) || userRepo.existsByEmail(dto.getEmail())) {
            return false;
        }
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

    public boolean isUsernameTaken(String username) {
        return userRepo.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return userRepo.existsByEmail(email);
    }
}