package org.AirlineReservationSystem.service;

import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlightService {
	private final FlightRepository flightRepo;

	public List<Flight> findAll() {
		return flightRepo.findAll();
	}

	public List<Flight> search(String origin, String destination, LocalDateTime start, LocalDateTime end) {
		return flightRepo.findByOriginAndDestinationAndDepartureTimeBetween(origin, destination, start, end);
	}

	public Optional<Flight> findById(Long id) {
		return flightRepo.findById(id);
	}

	@Transactional
	public Flight save(Flight flight) {
		// initialize availability if new
		if (flight.getEconomySeatsAvailable() == 0 && flight.getBusinessSeatsAvailable() == 0) {
			flight.setEconomySeatsAvailable(flight.getTotalEconomySeats());
			flight.setBusinessSeatsAvailable(flight.getTotalBusinessSeats());
		}
		return flightRepo.save(flight);
	}

	@Transactional
	public void delete(Long id) {
		flightRepo.deleteById(id);
	}

	@Transactional
	public void updateAvailability(Long id, int economyDelta, int businessDelta) {
		Flight flight = flightRepo.findById(id).orElseThrow();
		flight.setEconomySeatsAvailable(flight.getEconomySeatsAvailable() + economyDelta);
		flight.setBusinessSeatsAvailable(flight.getBusinessSeatsAvailable() + businessDelta);
		flightRepo.save(flight);
	}
}