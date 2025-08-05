package org.AirlineReservationSystem.config;

import org.AirlineReservationSystem.repository.UserRepository;
import org.AirlineReservationSystem.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1) Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) Load UserDetails from the database via UserRepository
    @Bean
    public UserDetailsService userDetailsService(CustomUserDetailsService svc) {
        return svc;
    }

    // 3) Wire the authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService uds,
            PasswordEncoder encoder
    ) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(uds);
        auth.setPasswordEncoder(encoder);
        return auth;
    }

    // 4) Define the HTTP security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // -- URL authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/home", "/register", "/css/**", "/js/**", "/images/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                // -- Form login config
                .formLogin(form -> form
                        .loginPage("/login")              // your custom login page URL
                        .loginProcessingUrl("/login")     // POST endpoint that Spring handles
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                // -- Logout config
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                // -- Access denied handler
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                );

        // let Spring apply the DaoAuthenticationProvider
        http.authenticationProvider(authenticationProvider(userDetailsService(null), passwordEncoder()));

        return http.build();
    }
}
