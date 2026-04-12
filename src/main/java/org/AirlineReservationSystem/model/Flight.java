package org.airlinereservationsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "flights", indexes = { @Index(name = "idx_flight_origin", columnList = "origin"),
		@Index(name = "idx_flight_destination", columnList = "destination"),
		@Index(name = "idx_flight_departure", columnList = "departureTime") })
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "flight", fetch = FetchType.LAZY)
	private List<Booking> bookings;

	@Transient
	private boolean hasBookings;

	@Column(nullable = false, unique = true)
	private String flightNumber;

	@Column(nullable = false)
	private String origin;

	@Column(nullable = false)
	private String destination;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime departureTime;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime arrivalTime;

	@Column(nullable = false)
	private int totalEconomySeats;

	@Column(nullable = false)
	private int totalBusinessSeats;

	@Column(nullable = false)
	private int economySeatsAvailable;

	@Column(nullable = false)
	private int businessSeatsAvailable;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal priceEconomy;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal priceBusiness;

	public Flight() {
		// Default constructor for JPA
	}

	@Transient
	public String getDepartureDisplay() {
		return org.airlinereservationsystem.util.DateUtils.formatForDisplay(this.getDepartureTime());
	}

	@Transient
	public String getArrivalDisplay() {
		return org.airlinereservationsystem.util.DateUtils.formatForDisplay(this.getArrivalTime());
	}

	@PrePersist
	@PreUpdate
	private void validateTimes() {
		if (departureTime != null && arrivalTime != null && arrivalTime.isBefore(departureTime)) {
			throw new IllegalArgumentException("Arrival must be after departure");
		}
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy
				? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
				: o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
				: this.getClass();
		if (thisEffectiveClass != oEffectiveClass)
			return false;
		Flight flight = (Flight) o;
		return getId() != null && Objects.equals(getId(), flight.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}
}
