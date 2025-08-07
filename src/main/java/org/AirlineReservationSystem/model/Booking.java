package org.AirlineReservationSystem.model;

import jakarta.persistence.*;
<<<<<<< Updated upstream
import lombok.*;
=======
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
>>>>>>> Stashed changes
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
<<<<<<< Updated upstream
@AllArgsConstructor
@NoArgsConstructor
=======
@RequiredArgsConstructor
>>>>>>> Stashed changes
@Entity
@Table(name = "booking")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

<<<<<<< Updated upstream
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(nullable = false)
    private Integer seatNumber;

    @Column(nullable = false)
    private BigDecimal pricePaid;

    @Column(nullable = false)
    private LocalDateTime bookedAt;
=======
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@ToString.Exclude
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flight_id", nullable = false)
	@ToString.Exclude
	private Flight flight;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SeatClass seatClass; // "ECONOMY" or "BUSINESS"

	@Column(nullable = false)
	private int seats;
>>>>>>> Stashed changes

	@Column(nullable = false)
	private LocalDateTime bookingTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookingStatus status; // "BOOKED" or "CANCELLED"

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