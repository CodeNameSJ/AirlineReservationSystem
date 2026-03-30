package org.airlinereservationsystem.config;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationPropertiesTest {

	@Test
	void usesEnvironmentBackedDatabaseDefaultsAndDisablesSchemaMutation() throws IOException {
		Properties properties = new Properties();
		try (InputStream inputStream = new ClassPathResource("application.properties").getInputStream()) {
			properties.load(inputStream);
		}

		assertThat(properties.getProperty("spring.datasource.url"))
				.isEqualTo("${AIRLINE_DB_URL:jdbc:mysql://localhost:3306/airline_db?createDatabaseIfNotExist=true}");
		assertThat(properties.getProperty("spring.datasource.username"))
				.isEqualTo("${AIRLINE_DB_USERNAME:root}");
		assertThat(properties.getProperty("spring.datasource.password"))
				.isEqualTo("${AIRLINE_DB_PASSWORD:root}");
		assertThat(properties.getProperty("spring.jpa.hibernate.ddl-auto")).isEqualTo("none");
		assertThat(properties.getProperty("spring.jpa.generate-ddl")).isEqualTo("false");
	}
}
