package org.airlinereservationsystem.service;

import lombok.RequiredArgsConstructor;
import org.airlinereservationsystem.model.User;
import org.airlinereservationsystem.model.enums.Role;
import org.airlinereservationsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepo;

	public void save(User user) {
		// If creating a new user, ensure a role is set; preserve password hashing if needed
		if (user.getRole() == null) user.setRole(Role.USER);
		// TODO: encode password if you add PasswordEncoder later
		userRepo.save(user);
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