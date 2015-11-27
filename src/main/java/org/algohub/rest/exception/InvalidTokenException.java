package org.algohub.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class InvalidTokenException extends Exception {
  private final String token;

  public InvalidTokenException(final String token) {
    super(String.format("Invalid token %s", token));
    this.token = token;
  }
}
