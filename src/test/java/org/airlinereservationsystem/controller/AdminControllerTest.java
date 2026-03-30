package org.airlinereservationsystem.controller;

import org.airlinereservationsystem.model.Flight;
import org.airlinereservationsystem.service.BookingService;
import org.airlinereservationsystem.service.FlightService;
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
class AdminControllerTest {

	@Mock
	private FlightService flightService;

	@Mock
	private BookingService bookingService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new AdminController(flightService, bookingService)).build();
	}

	@Test
	void redirectsFlightEditRouteToCanonicalFlightNumber() throws Exception {
		Flight flight = new Flight();
		flight.setId(5L);
		flight.setFlightNumber("AI101");

		when(flightService.findByFlightNumber("ai101")).thenReturn(Optional.of(flight));

		mockMvc.perform(get("/admin/flights/edit/ai101").sessionAttr("role", "ADMIN"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/flights/edit/AI101"));
	}

	@Test
	void loadsEditFlightFormByFlightNumber() throws Exception {
		Flight flight = new Flight();
		flight.setId(5L);
		flight.setFlightNumber("AI101");

		when(flightService.findByFlightNumber("AI101")).thenReturn(Optional.of(flight));

		mockMvc.perform(get("/admin/flights/edit/AI101").sessionAttr("role", "ADMIN"))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/flight-form"))
				.andExpect(model().attributeExists("flight"));
	}
}
