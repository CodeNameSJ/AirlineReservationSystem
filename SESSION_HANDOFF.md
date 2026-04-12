# Session Handoff

Use this file as a quick handoff for a new Codex chat in this repository.

## Paste-Ready Prompt

```text
You are working in C:\Users\jamad\Workspace\AirlineReservationSystem.

Start by reading:
1. AGENTS.md
2. PROJECT_CONTEXT.md
3. pom.xml
4. src/main/resources/application.properties
5. .github/workflows/build.yml

Then inspect these core application files:
- src/main/java/org/airlinereservationsystem/controller/PublicController.java
- src/main/java/org/airlinereservationsystem/controller/UserController.java
- src/main/java/org/airlinereservationsystem/controller/AdminController.java
- src/main/java/org/airlinereservationsystem/service/BookingService.java

Project summary:
- This is a Spring Boot 4 / Java 21 airline booking application with Thymeleaf views.
- It uses Spring Data JPA, Flyway, MySQL, and Maven wrapper commands.
- Public, user, and admin flows are split across MVC controllers and templates.

Upgrade context:
- The repo already contains Java-upgrade artifacts under .github/java-upgrade/.
- The current pom.xml is on Spring Boot 4.0.5 and Java 21.
- CI in .github/workflows/build.yml also uses Java 21.
- Database defaults come from environment-backed properties in application.properties.
- There is a legacy CoreJava_OOP_Version folder, but the active app is the Spring Boot code under src/main/java/org/airlinereservationsystem/.

Important constraints:
- Do not modify CoreJava_OOP_Version unless explicitly asked.
- Keep persistence, service logic, templates, and tests aligned when changing behavior.
- Prefer implementing business-rule changes in services rather than controllers.

Useful commands:
- .\mvnw.cmd test
- .\mvnw.cmd clean test
- .\mvnw.cmd package
- .\mvnw.cmd spring-boot:run

If the task is specifically about the upgrade, also read:
- .github/java-upgrade/20260328164228/plan.md

After reading those files, summarize the current application structure, the upgrade state, and ask what specific task to perform next.
```

## Upgrade Notes

- Existing upgrade planning artifacts are in `.github/java-upgrade/`.
- The current checked-in application already targets Java 21.
- Tests exist for config, controllers, and services under `src/test/java/`.

## Notes

- Repo-local startup context is in `PROJECT_CONTEXT.md`.
- Repo-specific working rules are in `AGENTS.md`.
- Feature workflow artifacts should go in `ai-specs/`.
