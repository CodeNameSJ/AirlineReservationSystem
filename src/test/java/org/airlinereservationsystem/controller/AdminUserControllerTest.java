package org.airlinereservationsystem.controller;

import org.airlinereservationsystem.model.User;
import org.airlinereservationsystem.model.enums.Role;
import org.airlinereservationsystem.service.BookingService;
import org.airlinereservationsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class AdminUserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private BookingService bookingService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new AdminUserController(userService, bookingService)).build();
	}

	@Test
	void redirectsUsernameEditRouteToCanonicalLowercaseUsername() throws Exception {
		User user = new User();
		user.setId(7L);
		user.setUsername("alice");
		user.setRole(Role.USER);

		when(userService.findByUsername("ALICE")).thenReturn(Optional.of(user));

		mockMvc.perform(get("/admin/users/edit/ALICE").sessionAttr("role", "ADMIN"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/users/edit/alice"));
	}

	@Test
	void loadsEditFormByUsername() throws Exception {
		User user = new User();
		user.setId(7L);
		user.setUsername("alice");
		user.setRole(Role.ADMIN);

		when(userService.findByUsername("alice")).thenReturn(Optional.of(user));

		mockMvc.perform(get("/admin/users/edit/alice").sessionAttr("role", "ADMIN"))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/user-form"))
				.andExpect(model().attributeExists("user", "roles"));
	}
}
