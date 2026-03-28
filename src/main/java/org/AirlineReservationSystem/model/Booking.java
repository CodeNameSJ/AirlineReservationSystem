package org.airlinereservationsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.airlinereservationsystem.model.enums.BookingStatus;
import org.airlinereservationsystem.model.enums.SeatClass;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "bookings", indexes = {@Index(name = "idx_booking_user", columnList = "user_id"), @Index(name = "idx_booking_flight", columnList = "flight_id")})
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flight_id", nullable = false)
	private Flight flight;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SeatClass seatClass;

	@Column(nullable = false)
	private int seats;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal totalAmount;

	@Column(nullable = false)
	private LocalDateTime bookingTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookingStatus status;

	public Booking() {
	}

	private void validate() {
		if (seats <= 0) {
			throw new IllegalArgumentException("Seats must be greater than 0");
		}
	}

	@PrePersist
	private void prePersist() {
		if (bookingTime == null) {
			bookingTime = LocalDateTime.now();
		}
		if (status == null) {
			status = BookingStatus.BOOKED;
		}
		validate();
	}

	@PreUpdate
	private void preUpdate() {
		validate();
	}

	@Transient
	public String getBookingTimeDisplay() {
		return org.airlinereservationsystem.util.DateUtils.formatForDisplay(this.getBookingTime());
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		Booking booking = (Booking) o;
		return getId() != null && Objects.equals(getId(), booking.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}
