package org.AirlineReservationSystem.service;

import org.AirlineReservationSystem.model.Flight;
import org.AirlineReservationSystem.model.enums.SeatClass;
import org.AirlineReservationSystem.repository.FlightRepository;
import org.AirlineReservationSystem.util.ErrorConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FlightService {
	private final FlightRepository flightRepo;

	public FlightService(FlightRepository flightRepo) {
		this.flightRepo = flightRepo;
	}

	public List<Flight> findAll() {
		return flightRepo.findAll();
	}

	public List<Flight> search(String origin, String destination, LocalDateTime start, LocalDateTime end) {
		boolean hasOrigin = origin != null && !origin.isBlank();
		boolean hasDestination = destination != null && !destination.isBlank();
		boolean hasDateRange = (start != null && end != null);

		if (!hasOrigin && !hasDestination) {
			return hasDateRange ? flightRepo.findByDepartureTimeBetween(start, end) : flightRepo.findAll();
		}

		if (hasOrigin && hasDestination) {
			return hasDateRange ? flightRepo.findByOriginAndDestinationAndDepartureTimeBetween(origin, destination, start, end) : flightRepo.findByOriginAndDestination(origin, destination);
		}

		if (hasOrigin) {
			return hasDateRange ? flightRepo.findByOriginAndDepartureTimeBetween(origin, start, end) : flightRepo.findByOrigin(origin);
		}

		return hasDateRange ? flightRepo.findByDestinationAndDepartureTimeBetween(destination, start, end) : flightRepo.findByDestination(destination);

	}

	public Optional<Flight> findById(Long id) {
		return flightRepo.findById(id);
	}

	public Optional<Flight> findByFlightNumber(String flightNumber) {
		if (flightNumber == null) return Optional.empty();
		return flightRepo.findByFlightNumber(normalizeFlightNumber(flightNumber));
	}

	public List<String> originAirports() {
		return flightRepo.findDistinctOrigins();
	}

	public List<String> destinationAirports() {
		return flightRepo.findDistinctDestinations();
	}

	@Transactional
	public void save(Flight flight) {
		normalizeFlight(flight);

		if (flight.getTotalEconomySeats() < 0 || flight.getTotalBusinessSeats() < 0) {
			throw new IllegalArgumentException(ErrorConstants.NEGATIVE_SEAT_COUNTS_ERROR.getMessage());
		}

		flightRepo.findByFlightNumber(flight.getFlightNumber()).filter(existing -> flight.getId() == null || !existing.getId().equals(flight.getId())).ifPresent(existing -> {
			throw new IllegalArgumentException(ErrorConstants.FLIGHT_NUMBER_EXISTS_ERROR.getMessage());
		});

		if (flight.getId() == null) {
			// new flight
			flight.setEconomySeatsAvailable(flight.getTotalEconomySeats());
			flight.setBusinessSeatsAvailable(flight.getTotalBusinessSeats());
		} else {
			// existing flight → preserve availability
			Flight existing = flightRepo.findById(flight.getId()).orElseThrow();

			flight.setEconomySeatsAvailable(existing.getEconomySeatsAvailable());
			flight.setBusinessSeatsAvailable(existing.getBusinessSeatsAvailable());
		}

		flightRepo.save(flight);
	}

	@Transactional
	public void delete(Long id) {
		flightRepo.deleteById(id);
	}

	@Transactional
	public int reserveSeatsAtomic(Long flightId, SeatClass seatClass, int seats) {

		if (seatClass == null) {
			throw new IllegalArgumentException(ErrorConstants.SEAT_CLASS_REQUIRED_ERROR.getMessage());
		}

		if (seats <= 0) {
			throw new IllegalArgumentException(ErrorConstants.SEATS_MUST_BE_GREATER_THAN_ZERO_ERROR.getMessage());
		}

		int updated = (seatClass == SeatClass.ECONOMY) ? flightRepo.reserveEconomySeats(flightId, seats) : flightRepo.reserveBusinessSeats(flightId, seats);

		if (updated == 0) {
			if (!flightRepo.existsById(flightId)) {
				throw new IllegalArgumentException(ErrorConstants.NO_FLIGHT_FOUND_ERROR.getMessage());
			}
			throw new IllegalStateException(ErrorConstants.NOT_ENOUGH_SEATS_AVAILABLE_ERROR.getMessage());
		}

		return updated;
	}

	@Transactional
	public int releaseSeatsAtomic(Long flightId, SeatClass seatClass, int seats) {

		if (seatClass == null) {
			throw new IllegalArgumentException(ErrorConstants.SEAT_CLASS_REQUIRED_ERROR.getMessage());
		}

		if (seats <= 0) {
			throw new IllegalArgumentException(ErrorConstants.SEATS_MUST_BE_GREATER_THAN_ZERO_ERROR.getMessage());
		}

		int updated = (seatClass == SeatClass.ECONOMY) ? flightRepo.releaseEconomySeats(flightId, seats) : flightRepo.releaseBusinessSeats(flightId, seats);

		if (updated == 0) {
			if (!flightRepo.existsById(flightId)) {
				throw new IllegalArgumentException(ErrorConstants.NO_FLIGHT_FOUND_ERROR.getMessage());
			}
			throw new IllegalStateException(ErrorConstants.SEAT_RELEASE_EXCEEDS_CAPACITY_ERROR.getMessage());
		}

		return updated;
	}

	private void normalizeFlight(Flight flight) {
		if (flight.getFlightNumber() != null) {
			flight.setFlightNumber(normalizeFlightNumber(flight.getFlightNumber()));
		}
		if (flight.getOrigin() != null) {
			flight.setOrigin(flight.getOrigin().trim());
		}
		if (flight.getDestination() != null) {
			flight.setDestination(flight.getDestination().trim());
		}
	}

	private String normalizeFlightNumber(String flightNumber) {
		return flightNumber.trim().toUpperCase();
	}
}
