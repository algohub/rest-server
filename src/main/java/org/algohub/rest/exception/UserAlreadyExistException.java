package org.algohub.rest.exception;


public class UserAlreadyExistException extends Exception {
  private final long userId;

  public UserAlreadyExistException(final long userId) {
    super(String.format("User with id=%d already exists", userId));
    this.userId = userId;
  }
}
