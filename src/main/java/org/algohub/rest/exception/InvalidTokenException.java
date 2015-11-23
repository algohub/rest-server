package org.algohub.rest.exception;


public class InvalidTokenException extends Exception {
  private final String token;

  public InvalidTokenException(final String token) {
    super(String.format("Invalid token %s", token));
    this.token = token;
  }
}
