package org.airlinereservationsystem.service;

import org.airlinereservationsystem.model.User;
import org.airlinereservationsystem.model.enums.Role;
import org.airlinereservationsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public void save(User user) {
		if (user.getRole() == null) user.setRole(Role.USER);
		if (user.getId() != null) {
			User existing = userRepo.findById(user.getId()).orElseThrow();
			if (user.getPassword() == null || user.getPassword().isBlank()) {
				user.setPassword(existing.getPassword());
			}
		}
		if (!isEncodedPassword(user.getPassword())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		userRepo.save(user);
	}

	public boolean passwordMatches(User user, String rawPassword) {
		String storedPassword = user.getPassword();
		if (isEncodedPassword(storedPassword)) {
			return passwordEncoder.matches(rawPassword, storedPassword);
		}
		boolean matchesLegacyPassword = storedPassword != null && storedPassword.equals(rawPassword);
		if (matchesLegacyPassword) {
			user.setPassword(passwordEncoder.encode(rawPassword));
			userRepo.save(user);
		}
		return matchesLegacyPassword;
	}

	private boolean isEncodedPassword(String password) {
		return password != null && password.startsWith("$2");
	}

	public void delete(User user) {
		userRepo.delete(user);
	}

	public void delete(Long id) {
		userRepo.deleteById(id);
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}

	public Optional<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public Optional<User> findById(Long id) {
		return userRepo.findById(id);
	}
}
