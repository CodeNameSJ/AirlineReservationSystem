package org.airlinereservationsystem.repository;

import org.airlinereservationsystem.model.User;
import org.airlinereservationsystem.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	List<User> findByRole(Role role);
}