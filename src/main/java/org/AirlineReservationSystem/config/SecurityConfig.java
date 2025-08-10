package org.AirlineReservationSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

@Configuration
public class SecurityConfig {

	// password encoder used by your UserService (DB)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// Keep CSRF enabled for normal forms. If you temporarily disable while debugging, remove in production.
				//.csrf(AbstractHttpConfigurer::disable)

				.authorizeHttpRequests(auth -> auth
						                               // static resources
						                               .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/resources/**").permitAll()

						                               // public pages (home, search, flights listing)
						                               .requestMatchers("/", "/home", "/flights", "/search", "/login", "/register", "/perform_login").permitAll()

						                               // Booking and user-only actions. Protect POST/GET for booking paths:
						                               // any URL under /user/** requires ROLE_USER
						                               .requestMatchers("/user/**").hasRole("USER")

						                               // admin pages
						                               .requestMatchers("/admin/**").hasRole("ADMIN")

						                               // all other requests require authentication
						                               .anyRequest().authenticated())

				.formLogin(form -> form
						                   // single login page for both roles
						                   .loginPage("/login").loginProcessingUrl("/perform_login").successHandler(roleBasedAuthSuccessHandler()).failureUrl("/login?error").permitAll())

				.logout(logout -> logout.logoutUrl("/perform_logout").logoutSuccessUrl("/login?logout").invalidateHttpSession(true).deleteCookies("JSESSIONID"));

		return http.build();
	}

	// Redirect users based on a role after successful login
	@Bean
	public AuthenticationSuccessHandler roleBasedAuthSuccessHandler() {
		return (request, response, authentication) -> {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			String context = request.getContextPath();

			try {
				if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
					response.sendRedirect(context + "/admin/dashboard");
				} else {
					// default user landing page after login
					response.sendRedirect(context + "/user/home");
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		};
	}
}
