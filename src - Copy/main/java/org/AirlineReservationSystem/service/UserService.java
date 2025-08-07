package org.AirlineReservationSystem.service;

<<<<<<< Updated upstream
import org.AirlineReservationSystem.dto.UserRegistrationDTO;
import org.AirlineReservationSystem.model.UserAccount;
import org.AirlineReservationSystem.model.UserRole;
import org.AirlineReservationSystem.repository.UserAccountRepository;
=======
import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.Role;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
>>>>>>> Stashed changes
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository userRepo;
	private final PasswordEncoder encoder;

<<<<<<< Updated upstream
    private final UserAccountRepository userRepo;
=======
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).roles(user.getRole().name()).build();
	}
>>>>>>> Stashed changes

	@Transactional
	public User register(User user) {
		// hash password and set role
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(Role.USER);
		return userRepo.save(user);
	}

<<<<<<< Updated upstream
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
=======
	public Optional<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
>>>>>>> Stashed changes
}