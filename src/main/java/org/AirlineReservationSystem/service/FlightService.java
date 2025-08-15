package org.AirlineReservationSystem.service;

import lombok.RequiredArgsConstructor;
import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlightService {
	private final FlightRepository flightRepo;

	public List<Flight> findAll() {
		return flightRepo.findAll();
	}

	public List<Flight> search(String origin, String destination, LocalDateTime start, LocalDateTime end) {
		boolean hasOrigin = origin != null && !origin.isBlank();
		boolean hasDestination = destination != null && !destination.isBlank();
		boolean hasDateRange = (start != null && end != null);

		if (!hasOrigin && !hasDestination) {
			if (!hasDateRange) {
				return flightRepo.findAll();
			} else {
				return flightRepo.findByDepartureTimeBetween(start, end);
			}
		}

		if (hasOrigin && hasDestination) {
			if (hasDateRange) {
				return flightRepo.findByOriginAndDestinationAndDepartureTimeBetween(origin, destination, start, end);
			} else {
				return flightRepo.findByOriginAndDestination(origin, destination);
			}
		} else if (hasOrigin) {
			if (hasDateRange) {
				return flightRepo.findByOriginAndDepartureTimeBetween(origin, start, end);
			} else {
				return flightRepo.findByOrigin(origin);
			}
		} else if (hasDestination) {
			if (hasDateRange) {
				return flightRepo.findByDestinationAndDepartureTimeBetween(destination, start, end);
			} else {
				return flightRepo.findByDestination(destination);
			}
		}

		return flightRepo.findAll();
	}

	public Optional<Flight> findById(Long id) {
		return flightRepo.findById(id);
	}

	@Transactional
	public void save(Flight flight) {
		if (flight.getEconomySeatsAvailable() == 0 && flight.getBusinessSeatsAvailable() == 0) {
			flight.setEconomySeatsAvailable(flight.getTotalEconomySeats());
			flight.setBusinessSeatsAvailable(flight.getTotalBusinessSeats());
		}
		flightRepo.save(flight);
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