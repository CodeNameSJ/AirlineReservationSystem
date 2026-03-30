package org.airlinereservationsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.airlinereservationsystem.model.Booking;
import org.airlinereservationsystem.model.Flight;
import org.airlinereservationsystem.model.enums.SeatClass;
import org.airlinereservationsystem.service.BookingService;
import org.airlinereservationsystem.service.FlightService;
import org.airlinereservationsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminBookingControllerTest {

	@Mock
	private BookingService bookingService;

	@Mock
	private UserService userService;

	@Mock
	private FlightService flightService;

	@Mock
	private HttpServletRequest request;

	@Test
	void saveBookingCreatesWhenIdIsNull() {
		AdminBookingController controller = new AdminBookingController(bookingService, userService, flightService);

		Booking booking = new Booking();
		booking.setId(15L);
		Flight flight = new Flight();
		flight.setFlightNumber("AI101");
		booking.setFlight(flight);

		when(request.getSession(false)).thenReturn(new org.springframework.mock.web.MockHttpSession());
		request.getSession(false).setAttribute("role", "ADMIN");
		when(bookingService.createBooking(3L, 4L, SeatClass.BUSINESS, 2)).thenReturn(booking);

		String view = controller.saveBooking(request, null, 3L, 4L, 2, SeatClass.BUSINESS, null,
				new ConcurrentModel(), new RedirectAttributesModelMap());

		assertThat(view).isEqualTo("redirect:/admin/bookings/edit/15");
		verify(bookingService).createBooking(3L, 4L, SeatClass.BUSINESS, 2);
		verify(bookingService, never()).updateBooking(any(), any(), any(), any(), anyInt(), any());
	}
}
