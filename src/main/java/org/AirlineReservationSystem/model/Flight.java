package org.airlinereservationsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime departureTime;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime arrivalTime;

	private int totalEconomySeats;
	private int totalBusinessSeats;
	private int economySeatsAvailable;
	private int businessSeatsAvailable;

	@Column(nullable = false)
	private BigDecimal priceEconomy;

	@Column(nullable = false)
	private BigDecimal priceBusiness;

	public Flight() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public boolean isHasBookings() {
		return hasBookings;
	}

	public void setHasBookings(boolean hasBookings) {
		this.hasBookings = hasBookings;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getTotalEconomySeats() {
		return totalEconomySeats;
	}

	public void setTotalEconomySeats(int totalEconomySeats) {
		this.totalEconomySeats = totalEconomySeats;
	}

	public int getTotalBusinessSeats() {
		return totalBusinessSeats;
	}

	public void setTotalBusinessSeats(int totalBusinessSeats) {
		this.totalBusinessSeats = totalBusinessSeats;
	}

	public int getEconomySeatsAvailable() {
		return economySeatsAvailable;
	}

	public void setEconomySeatsAvailable(int economySeatsAvailable) {
		this.economySeatsAvailable = economySeatsAvailable;
	}

	public int getBusinessSeatsAvailable() {
		return businessSeatsAvailable;
	}

	public void setBusinessSeatsAvailable(int businessSeatsAvailable) {
		this.businessSeatsAvailable = businessSeatsAvailable;
	}

	public BigDecimal getPriceEconomy() {
		return priceEconomy;
	}

	public void setPriceEconomy(BigDecimal priceEconomy) {
		this.priceEconomy = priceEconomy;
	}

	public BigDecimal getPriceBusiness() {
		return priceBusiness;
	}

	public void setPriceBusiness(BigDecimal priceBusiness) {
		this.priceBusiness = priceBusiness;
	}

	@Transient
	public String getDepartureDisplay() {
		return org.airlinereservationsystem.util.DateUtils.formatForDisplay(this.getDepartureTime());
	}

	@Transient
	public String getArrivalDisplay() {
		return org.airlinereservationsystem.util.DateUtils.formatForDisplay(this.getArrivalTime());
	}

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
