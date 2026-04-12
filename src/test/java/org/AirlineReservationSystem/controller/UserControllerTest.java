package org.AirlineReservationSystem.controller;

import org.AirlineReservationSystem.model.Booking;
import org.AirlineReservationSystem.model.User;
import org.AirlineReservationSystem.service.BookingService;
import org.AirlineReservationSystem.service.FlightService;
import org.AirlineReservationSystem.service.PricingBreakdown;
import org.AirlineReservationSystem.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private FlightService flightService;

	@Mock
	private BookingService bookingService;

	@Mock
	private PricingService pricingService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new UserController(flightService, bookingService, pricingService)).build();
	}

	@Test
	void redirectsWhenBookingDoesNotBelongToCurrentUser() throws Exception {
		User owner = new User();
		owner.setId(7L);

		Booking booking = new Booking();
		booking.setId(99L);
		booking.setUser(owner);

		when(bookingService.findById(99L)).thenReturn(Optional.of(booking));

		mockMvc.perform(get("/user/booking/99").sessionAttr("userId", 8L)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user/home"));
	}

	@Test
	void loadsBillForOwnedBookingWithPricingBreakdown() throws Exception {
		User owner = new User();
		owner.setId(7L);

		Booking booking = new Booking();
		booking.setId(99L);
		booking.setUser(owner);

		when(bookingService.findById(99L)).thenReturn(Optional.of(booking));
		when(pricingService.calculateBreakdown(booking)).thenReturn(new PricingBreakdown(new BigDecimal("9000.00"), new BigDecimal("900.00"), new BigDecimal("50.00"), new BigDecimal("9950.00")));

		mockMvc.perform(get("/user/booking/99/bill").sessionAttr("userId", 7L)).andExpect(status().isOk()).andExpect(view().name("user/bookingBill")).andExpect(model().attributeExists("booking", "pricing"));
	}

	@Test
	void cancelAddsSuccessFlashMessage() throws Exception {
		mockMvc.perform(post("/user/bookings/cancel").sessionAttr("userId", 7L).param("id", "99")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user/home")).andExpect(flash().attribute("successMessage", "Booking canceled successfully."));

		verify(bookingService).cancelBookingForUser(99L, 7L);
	}
}
