package org.AirlineReservationSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// ——— ADMIN CHAIN ———
	@Bean
	@Order(1)
	public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
		http
				// only apply this chain to URLs under /admin
				.securityMatcher("/admin/**")
				.authorizeHttpRequests(auth -> auth
						                               // allow everyone to see the admin login page and static assets
						                               .requestMatchers("/admin/login", "/css/**", "/js/**", "/images/**").permitAll()
						                               // everything else under /admin/** requires ADMIN role
						                               .anyRequest().hasRole("ADMIN")
				)
				.formLogin(form -> form
						                   .loginPage("/admin/login")
						                   .loginProcessingUrl("/admin/processLogin")
						                   .defaultSuccessUrl("/admin/dashboard", true)
						                   .permitAll()
				)
				.logout(logout -> logout
						                  .logoutUrl("/admin/logout")
						                  .logoutSuccessUrl("/admin/login?logout")
						                  .permitAll()
				)
				.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

	// ——— USER CHAIN ———
	@Bean
	@Order(2)
	public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
		http
				.securityMatcher("/user/**")
				.authorizeHttpRequests(auth -> auth
						                               .requestMatchers("/user/login", "/user/register", "/css/**", "/js/**", "/images/**")
						                               .permitAll()
						                               .anyRequest().hasRole("USER")
				)
				.formLogin(form -> form
						                   .loginPage("/user/login")
						                   .loginProcessingUrl("/user/processLogin")
						                   .defaultSuccessUrl("/user/home", true)
						                   .permitAll()
				)
				.logout(logout -> logout
						                  .logoutUrl("/user/logout")
						                  .logoutSuccessUrl("/user/login?logout")
						                  .permitAll()
				)
				.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

	// ——— PUBLIC CHAIN ———
	@Bean
	@Order(3)
	public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
		http
				// this chain applies to *everything* (fallback)
				.authorizeHttpRequests(auth -> auth
						                               // public pages
						                               .requestMatchers(
								                               "/", "/index", "/home",
								                               "/register", "/registerUser", "/registerAdmin",
								                               "/loginUser", "/loginAdmin",
								                               "/css/**", "/js/**", "/images/**", "/webjars/**"
						                               )
						                               .permitAll()
						                               // any other URL must be authenticated (USER or ADMIN)
						                               .anyRequest().authenticated()
				)
				.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

	// ——— IN-MEMORY USERS (for testing/demo) ———
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		InMemoryUserDetailsManager mgr = new InMemoryUserDetailsManager();
		mgr.createUser(User.withUsername("user")
				               .password(encoder.encode("userPass"))
				               .roles("USER")
				               .build());
		mgr.createUser(User.withUsername("admin")
				               .password(encoder.encode("adminPass"))
				               .roles("ADMIN")
				               .build());
		return mgr;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}