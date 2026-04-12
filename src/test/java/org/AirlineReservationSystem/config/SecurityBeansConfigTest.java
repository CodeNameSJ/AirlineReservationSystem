package org.AirlineReservationSystem.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecurityBeansConfigTest {

  private final SecurityBeansConfig securityBeansConfig = new SecurityBeansConfig();

  @Test
  void exposesBcryptPasswordEncoder() {
    PasswordEncoder passwordEncoder = securityBeansConfig.passwordEncoder();

    assertThat(passwordEncoder).isInstanceOf(BCryptPasswordEncoder.class);
    assertThat(passwordEncoder.matches("secret", passwordEncoder.encode("secret"))).isTrue();
  }
}
