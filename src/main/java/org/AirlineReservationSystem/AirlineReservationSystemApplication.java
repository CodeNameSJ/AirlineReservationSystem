package org.AirlineReservationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org")
public class AirlineReservationSystemApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AirlineReservationSystemApplication.class, args);
	}
}