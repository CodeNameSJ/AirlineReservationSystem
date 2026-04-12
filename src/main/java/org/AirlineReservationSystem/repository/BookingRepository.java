package org.AirlineReservationSystem.repository;

import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.util.SeatReleaseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUser(User user);

	List<Booking> findByUserId(Long userId);

	boolean existsByFlightId(Long flightId);

	boolean existsByUserId(Long userId);

	List<Booking> findByFlightId(Long flightId);

	@Query("""
			    SELECT b.flight.id as flightId,
			           b.seatClass as seatClass,
			           SUM(b.seats) as totalSeats
			    FROM Booking b
			    WHERE b.flight.id = :flightId
			    AND b.status = 'BOOKED'
			    GROUP BY b.flight.id, b.seatClass
			""")
	List<SeatReleaseProjection> getSeatReleaseSummary(Long flightId);

	@Query("""
			    SELECT b.flight.id as flightId,
			           b.seatClass as seatClass,
			           SUM(b.seats) as totalSeats
			    FROM Booking b
			    WHERE b.user.id = :userId
			    AND b.status = 'BOOKED'
			    GROUP BY b.flight.id, b.seatClass
			""")
	List<SeatReleaseProjection> getSeatReleaseSummaryByUser(Long userId);

	@Modifying
	@Query("DELETE FROM Booking b WHERE b.flight.id = :flightId")
	void deleteByFlightId(Long flightId);

	@Modifying
	@Query("DELETE FROM Booking b WHERE b.user.id = :userId")
	void deleteByUserId(Long userId);
}