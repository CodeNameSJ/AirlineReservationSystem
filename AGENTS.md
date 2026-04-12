# Airline Reservation System Instructions

## Project Summary

`AirlineReservationSystem` is a Spring Boot 4 / Java 21 server-rendered web application
for browsing flights, authenticating users, booking seats, and managing flights,
bookings, and users through admin flows. The stack uses Thymeleaf for views, Spring Data
JPA for persistence, Flyway for schema migration, and MySQL as the default runtime
database.

## Repo Layout

- `src/main/java/org/airlinereservationsystem/` main application code.
- `controller/` MVC controllers for public, user, auth, and admin flows.
- `service/` booking, flight, pricing, and user business logic.
- `repository/` Spring Data repositories.
- `model/` JPA entities and enums.
- `config/` Spring bean configuration.
- `util/` shared helpers, validation exceptions, date formatting, projections.
- `src/main/resources/templates/` Thymeleaf templates.
- `src/main/resources/static/` CSS, JavaScript, and image assets.
- `src/main/resources/db/` SQL schema, seed data, and Flyway migration files.
- `src/test/java/` controller, service, and config tests.
- `web/` legacy web resources.
- `CoreJava_OOP_Version/` older standalone Java version kept alongside the Spring app.

## Key Paths

- `pom.xml` dependencies, Java version, and build plugins.
- `PROJECT_CONTEXT.md` quick-start repo summary for new sessions.
- `src/main/java/org/airlinereservationsystem/AirlineReservationSystemApplication.java`
  Spring Boot entrypoint.
- `src/main/resources/application.properties` datasource, Flyway, JPA, and pricing config.
- `src/main/java/org/airlinereservationsystem/controller/PublicController.java`
  public home, flight listing, and flight detail flows.
- `src/main/java/org/airlinereservationsystem/controller/UserController.java`
  user booking, cancellation, detail, and billing flows.
- `src/main/java/org/airlinereservationsystem/controller/AdminController.java`
  admin dashboard and flight management.
- `src/main/java/org/airlinereservationsystem/service/BookingService.java`
  seat reservation, cancellation, deletion, and transactional booking rules.
- `src/main/java/org/airlinereservationsystem/service/PricingService.java`
  fare calculation logic.
- `src/main/resources/templates/` and `src/main/resources/static/` user-facing UI.

## Commands

- `.\mvnw.cmd spring-boot:run`
- `.\mvnw.cmd test`
- `.\mvnw.cmd clean test`
- `.\mvnw.cmd package`

## Hard Rules

- Treat `src/main/java/org/airlinereservationsystem/` as the active application; do not
  modify `CoreJava_OOP_Version/` unless the task explicitly targets that legacy code.
- Keep database changes aligned across JPA models, repositories, services, and migration
  files in `src/main/resources/db/`.
- Prefer fixing business rules in services, not controllers.
- Preserve the server-rendered Thymeleaf architecture unless the user asks for a stack
  change.
- Respect current runtime assumptions: MySQL by default, Flyway enabled, `ddl-auto=none`.
- Add or update tests in `src/test/java/` for behavior changes when practical.

## Working Notes

- Session-based auth is used in controllers alongside password encoding via
  `SecurityBeansConfig`; there is not a full Spring Security web config in the current
  code path.
- Pricing config lives in `application.properties` and currently includes tax and service
  fee settings.
- The app includes public browsing pages, user booking pages, and separate admin pages
  under `templates/admin/` and `templates/user/`.
- Read `PROJECT_CONTEXT.md` first when orienting in a fresh session.
