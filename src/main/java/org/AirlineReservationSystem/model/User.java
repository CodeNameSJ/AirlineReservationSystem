package org.AirlineReservationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.AirlineReservationSystem.model.enums.Role;
import org.hibernate.proxy.HibernateProxy;

@Setter
@Getter
@Entity
@Table(
    name = "users",
    indexes = {
      @Index(name = "idx_users_username", columnList = "username"),
      @Index(name = "idx_users_email", columnList = "email")
    })
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Booking> bookings;

  @Transient private boolean hasBookings;

  @NotBlank
  @Size(min = 3, max = 50)
  @Column(nullable = false, unique = true)
  private String username;

  @NotBlank
  @Email(message = "Invalid email format")
  @Column(nullable = false, unique = true)
  private String email;

  @Size(min = 8, message = "Password must be at least 8 characters")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  public User() {
    // Default constructor for JPA
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    User user = (User) o;
    return getId() != null && Objects.equals(getId(), user.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
