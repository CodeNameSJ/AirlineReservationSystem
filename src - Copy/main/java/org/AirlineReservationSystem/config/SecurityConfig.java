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

	// Admin security chain
	@Configuration
	@Order(1)
	public static class AdminSecurityConfig {
		@Bean
		public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
			http.securityMatcher("/admin/**")
					.authorizeHttpRequests(auth -> auth.anyRequest().hasRole("ADMIN"))
					.formLogin(form -> form
							                   .loginPage("/loginAdmin")
							                   .loginProcessingUrl("/admin_login")
							                   .defaultSuccessUrl("/admin/dashboard"))
					.logout(logout -> logout
							                  .logoutUrl("/admin_logout")
							                  .logoutSuccessUrl("/loginAdmin?logout"))
					.csrf(AbstractHttpConfigurer::disable);

			return http.build();
		}
	}

	// User security chain
	@Configuration
	@Order(2)
	public static class UserSecurityConfig {
		@Bean
		public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
			http.securityMatcher("/user/**")
					.authorizeHttpRequests(auth -> auth.anyRequest().hasRole("USER"))
					.formLogin(form -> form
							                   .loginPage("/loginUser")
							                   .loginProcessingUrl("/user_login")
							                   .defaultSuccessUrl("/user/home"))
					.logout(logout -> logout
							                  .logoutUrl("/user_logout")
							                  .logoutSuccessUrl("/loginUser?logout"))
					.csrf(AbstractHttpConfigurer::disable);

			return http.build();
		}
	}

	// Public pages and general config
	@Bean
	public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						                               .requestMatchers("/login*", "/register", "/css/**").permitAll()
						                               .anyRequest().authenticated()
				)
				.formLogin(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

	// In-memory users for demo
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		InMemoryUserDetailsManager mgr = new InMemoryUserDetailsManager();
		mgr.createUser(User.withUsername("user").password(encoder.encode("userPass")).roles("USER").build());
		mgr.createUser(User.withUsername("admin").password(encoder.encode("adminPass")).roles("ADMIN").build());
		return mgr;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}