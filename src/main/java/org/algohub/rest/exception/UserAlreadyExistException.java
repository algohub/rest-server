package org.algohub.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public final class UserAlreadyExistException extends Exception {
  private final long userId;

  public UserAlreadyExistException(final long userId) {
    super(String.format("User with id=%d already exists", userId));
    this.userId = userId;
  }
}
