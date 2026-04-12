package org.AirlineReservationSystem.util;

import lombok.Getter;

@Getter
public class UserValidationException extends RuntimeException {

  private final String field;

  public UserValidationException(String field, String message) {
    super(message);
    this.field = field;
  }
}
