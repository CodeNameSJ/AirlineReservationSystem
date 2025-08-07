package org.AirlineReservationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirlineReservationSystemApplication {

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    public static void main(String[] args) {
        SpringApplication.run(AirlineReservationSystemApplication.class, args);
=======
=======
>>>>>>> Stashed changes
	public static void main(String[] args) {
		SpringApplication.run(AirlineReservationSystemApplication.class, args);
	}

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
>>>>>>> Stashed changes
    }
}