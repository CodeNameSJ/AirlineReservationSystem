package org.AirlineReservationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import java.math.BigDecimal;
import java.time.LocalDateTime;
>>>>>>> Stashed changes
=======
import java.math.BigDecimal;
import java.time.LocalDateTime;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream

<<<<<<< Updated upstream
    @ManyToOne(optional = false)
    @JoinColumn(name = "origin_id")
    private Airport origin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_id")
    private Airport destination;

    @Column(nullable = false)
    private String aircraftType;
=======
=======

>>>>>>> Stashed changes
	@Column(nullable = false, unique = true)
	private String flightNumber;

	@Column(nullable = false)
	private String origin;

	@Column(nullable = false)
	private String destination;

	@Column(nullable = false)
	private LocalDateTime departureTime;

	private int totalEconomySeats;
	private int totalBusinessSeats;
	private int economySeatsAvailable;
	private int businessSeatsAvailable;

	@Column(nullable = false)
	private BigDecimal priceEconomy;

	@Column(nullable = false)
	private BigDecimal priceBusiness;

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
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}