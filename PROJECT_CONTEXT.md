# Project Context

## What This Project Is

`AirlineReservationSystem` is a Spring Boot 4 / Java 21 airline booking application with
server-rendered Thymeleaf views. It supports public flight browsing, user login and
booking flows, and admin management of flights, bookings, and users.

## Main Flows

1. Public users browse flights and flight details.
2. Logged-in users create, view, bill, and cancel bookings.
3. Admin users manage flights, bookings, and users through MVC controllers and templates.
4. Services enforce booking, pricing, and seat-allocation rules.
5. Persistence flows through Spring Data repositories to MySQL, with Flyway migrations in
   `src/main/resources/db/`.

## Important Files

- `pom.xml`
- `src/main/java/org/airlinereservationsystem/AirlineReservationSystemApplication.java`
- `src/main/resources/application.properties`
- `src/main/java/org/airlinereservationsystem/controller/PublicController.java`
- `src/main/java/org/airlinereservationsystem/controller/UserController.java`
- `src/main/java/org/airlinereservationsystem/controller/AdminController.java`
- `src/main/java/org/airlinereservationsystem/service/BookingService.java`
- `src/main/java/org/airlinereservationsystem/service/FlightService.java`
- `src/main/java/org/airlinereservationsystem/service/PricingService.java`
- `src/main/resources/templates/`
- `src/main/resources/static/`
- `src/test/java/`

## Default Assumptions

- The active app is the Spring Boot code under `src/main/java/org/airlinereservationsystem/`.
- `CoreJava_OOP_Version/` is legacy/reference code and should not be changed unless the
  task explicitly targets it.
- MySQL is the default runtime database.
- Flyway is enabled and `spring.jpa.hibernate.ddl-auto=none`.
- Business-rule changes should usually be implemented in services, not controllers.
- User-facing changes often span controller, template, static asset, and test updates.

## Typical Commands

- `.\mvnw.cmd spring-boot:run`
- `.\mvnw.cmd test`
- `.\mvnw.cmd clean test`
- `.\mvnw.cmd package`
