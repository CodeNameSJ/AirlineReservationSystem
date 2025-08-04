package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
