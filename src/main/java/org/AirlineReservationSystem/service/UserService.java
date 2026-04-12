package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.model.enums.Role;
import org.AirlineReservationSystem.repository.UserRepository;
import org.AirlineReservationSystem.util.Constants;
import org.AirlineReservationSystem.util.UserValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void register(User user) {
		normalizeIdentity(user);

		if (user.getRole() == null) {
			user.setRole(Role.USER);
		}

		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new UserValidationException("password", Constants.PASSWORD_REQUIRED_ERROR.getMessage());
		}

		if (userRepo.findByUsername(user.getUsername()).isPresent()) {
			throw new UserValidationException("username", Constants.USERNAME_EXISTS_ERROR.getMessage());
		}

		if (userRepo.findByEmail(user.getEmail()).isPresent()) {
			throw new UserValidationException("email", Constants.EMAIL_EXISTS_ERROR.getMessage());
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}

	@Transactional
	public void update(User user) {
		normalizeIdentity(user);

		User existing = userRepo.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));

		userRepo.findByUsername(user.getUsername()).filter(found -> !found.getId().equals(existing.getId())).ifPresent(found -> {
			throw new UserValidationException("username", Constants.USERNAME_EXISTS_ERROR.getMessage());
		});

		userRepo.findByEmail(user.getEmail()).filter(found -> !found.getId().equals(existing.getId())).ifPresent(found -> {
			throw new UserValidationException("email", Constants.EMAIL_EXISTS_ERROR.getMessage());
		});

		// Password handling
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			user.setPassword(existing.getPassword());
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}

		// Role handling (allow admin change, preserve otherwise)
		if (user.getRole() == null) {
			user.setRole(existing.getRole());
		}

		userRepo.save(user);
	}

	@Transactional
	public boolean passwordMatches(User user, String rawPassword) {

		String stored = user.getPassword();

		if (stored == null) return false;

		// Normal encoded case
		if (stored.startsWith("$2")) {
			return passwordEncoder.matches(rawPassword, stored);
		}

		// Legacy plain-text fallback → migrate
		if (stored.equals(rawPassword)) {
			user.setPassword(passwordEncoder.encode(rawPassword));
			userRepo.save(user);
			return true;
		}

		return false;
	}

	@Transactional
	public void delete(User user) {
		userRepo.delete(user);
	}

	@Transactional
	public void delete(Long id) {
		userRepo.deleteById(id);
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}

	public Optional<User> findByUsername(String username) {
		if (username == null) return Optional.empty();
		return userRepo.findByUsername(username.trim().toLowerCase());
	}

	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public Optional<User> findById(Long id) {
		return userRepo.findById(id);
	}

	private void normalizeIdentity(User user) {
		if (user.getUsername() != null) {
			user.setUsername(user.getUsername().trim().toLowerCase());
		}
		if (user.getEmail() != null) {
			user.setEmail(user.getEmail().trim().toLowerCase());
		}
	}
}
