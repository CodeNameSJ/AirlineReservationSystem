package org.AirlineReservationSystem.service;

import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.model.enums.Role;
import org.AirlineReservationSystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepo;

	@Transactional
	public void save(User user) {
		// hash password and set role
		user.setPassword(user.getPassword());
		user.setRole(Role.USER);
		userRepo.save(user);
	}

	@Transactional
	public void delete(User user) {
		userRepo.delete(user);
	}

	@Transactional
	public void delete(Long id) {
		userRepo.deleteById(id);
	}

	@Transactional
	public List<User> findAll() {
		return userRepo.findAll();
	}

	public Optional<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
}