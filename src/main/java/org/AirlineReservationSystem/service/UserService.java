package org.AirlineReservationSystem.service;

import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.Role;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository userRepo;
	private final PasswordEncoder encoder;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();
	}

	@Transactional
	public User register(User user) {
		// hash password and set role
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(Role.USER);
		return userRepo.save(user);
	}

	public Optional<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
}