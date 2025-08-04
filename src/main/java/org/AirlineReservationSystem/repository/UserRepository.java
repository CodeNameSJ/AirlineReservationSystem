package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
}
