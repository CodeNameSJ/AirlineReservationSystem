package org.AirlineReservationSystem.security;

import lombok.Getter;
import org.AirlineReservationSystem.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Map each Role.name (e.g. "ADMIN") to a SimpleGrantedAuthority("ROLE_ADMIN")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // already BCrypt-encoded
    }

    @Override
    public String getUsername() {
        return user.getEmail();     // using email as principal
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;                // adjust if you have an expiry field
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();    // assuming your User has a `locked` flag
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;                // adjust if you track credential expiry
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();    // assuming your User has an `enabled` flag
    }

    // convenience accessor for your controllers/services
    public Long getId() {
        return user.getId();
    }
}