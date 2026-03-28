package org.airlinereservationsystem.service;

import org.airlinereservationsystem.model.User;
import org.airlinereservationsystem.model.enums.Role;
import org.airlinereservationsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	@Test
	void saveHashesPlaintextPasswordsAndDefaultsRoleForNewUsers() {
		User user = new User();
		user.setUsername("alice");
		user.setPassword("secret");

		when(Objects.requireNonNull(passwordEncoder.encode("secret"))).thenReturn("$2hashed");

		userService.register(user);

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(userCaptor.capture());
		assertThat(userCaptor.getValue().getRole()).isEqualTo(Role.USER);
		assertThat(userCaptor.getValue().getPassword()).isEqualTo("$2hashed");
	}

	@Test
	void saveKeepsExistingPasswordWhenEditingUserWithoutNewPassword() {
		User existingUser = new User();
		existingUser.setId(7L);
		existingUser.setPassword("$2existing");

		User editedUser = new User();
		editedUser.setId(7L);
		editedUser.setUsername("alice");
		editedUser.setPassword(" ");
		editedUser.setRole(Role.ADMIN);

		when(userRepository.findById(7L)).thenReturn(Optional.of(existingUser));

		userService.update(editedUser);

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(userCaptor.capture());
		verify(passwordEncoder, never()).encode(any());
		assertThat(userCaptor.getValue().getPassword()).isEqualTo("$2existing");
		assertThat(userCaptor.getValue().getRole()).isEqualTo(Role.ADMIN);
	}

	@Test
	void passwordMatchesMigratesLegacyPlaintextPasswords() {
		User user = new User();
		user.setPassword("legacy-secret");

		when(Objects.requireNonNull(passwordEncoder.encode("legacy-secret"))).thenReturn("$2upgraded");

		boolean matches = userService.passwordMatches(user, "legacy-secret");

		assertThat(matches).isTrue();
		assertThat(user.getPassword()).isEqualTo("$2upgraded");
		verify(userRepository).save(user);
	}

	@Test
	void passwordMatchesUsesEncoderForAlreadyHashedPasswords() {
		User user = new User();
		user.setPassword("$2stored");

		when(passwordEncoder.matches("secret", "$2stored")).thenReturn(true);

		boolean matches = userService.passwordMatches(user, "secret");

		assertThat(matches).isTrue();
		verify(passwordEncoder).matches("secret", "$2stored");
		verify(userRepository, never()).save(any());
	}
}
