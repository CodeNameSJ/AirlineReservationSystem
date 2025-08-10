package org.AirlineReservationSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder encoder, UserDetailsService userDetailsService) throws Exception {

		AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

		authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder);

		return authManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.requestMatchers("/resources/**", "/css/**", "/js/**", "/img/**", "/", "/home", "/flights", "/register", "/login", "/perform_login").permitAll().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER").anyRequest().authenticated()).formLogin(form -> form.loginProcessingUrl("/perform_login").loginPage("/login").successHandler(roleBasedAuthSuccessHandler()).failureUrl("/login?error").permitAll()).logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll());

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler roleBasedAuthSuccessHandler() {
		return (request, response, authentication) -> {
			String context = request.getContextPath();
			boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
			response.sendRedirect(context + (isAdmin ? "/admin/dashboard" : "/user/home"));
		};
	}
}