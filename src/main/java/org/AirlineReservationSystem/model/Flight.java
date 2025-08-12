package org.AirlineReservationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Transient
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
	private LocalDateTime departureTime;

	@Column(nullable = false)
	private LocalDateTime arrivalTime;

	private int totalEconomySeats;
	private int totalBusinessSeats;
	private int economySeatsAvailable;
	private int businessSeatsAvailable;

	@Column(nullable = false)
	private BigDecimal priceEconomy;

	@Column(nullable = false)
	private BigDecimal priceBusiness;

	private double price;

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Flight flight = (Flight) o;
		return getId() != null && Objects.equals(getId(), flight.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}