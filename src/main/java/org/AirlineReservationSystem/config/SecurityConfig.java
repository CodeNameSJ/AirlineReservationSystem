package org.AirlineReservationSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collection;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Auth manager (useful if you need to manually authenticate elsewhere)
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService uds, PasswordEncoder pe) throws Exception {
		AuthenticationManagerBuilder amb = http.getSharedObject(AuthenticationManagerBuilder.class);
		amb.userDetailsService(uds).passwordEncoder(pe);
		return amb.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// remove this line once forms are fixed (I left it here only to quickly test)
				.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
						                                                                     // static and public pages
						                                                                     .requestMatchers("/resources/**", "/css/**", "/js/**", "/img/**", "/", "/home", "/flights", "/register", "/loginUser", "/loginAdmin", "/perform_login").permitAll().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER").anyRequest().authenticated()).formLogin(form -> form
								                                                                                                                                                                                                                                                                                                                                                                                   // single processing URL for both forms
								                                                                                                                                                                                                                                                                                                                                                                                   .loginProcessingUrl("/perform_login")
								                                                                                                                                                                                                                                                                                                                                                                                   // we allow both login pages to be accessible directly; choose one as default for Spring redirects
								                                                                                                                                                                                                                                                                                                                                                                                   .loginPage("/loginUser").successHandler(roleBasedAuthSuccessHandler()).failureUrl("/loginUser?error").permitAll()).logout(logout -> logout.logoutUrl("/perform_logout").logoutSuccessUrl("/loginUser?logout").invalidateHttpSession(true).deleteCookies("JSESSIONID"));

		return http.build();
	}

	// redirect users based on a role after successful login
	@Bean
	public AuthenticationSuccessHandler roleBasedAuthSuccessHandler() {
		return (request, response, authentication) -> {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			String context = request.getContextPath();
			if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
				response.sendRedirect(context + "/admin/dashboard");
			} else {
				response.sendRedirect(context + "/user/home");
			}
		};
	}
}