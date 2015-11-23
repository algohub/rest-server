package org.algohub.rest.exception;


public class UserNotExistException extends Exception {
  private final long userId;

  public UserNotExistException(final long userId) {
    super(String.format("User with id=%d does not exist ", userId));
    this.userId = userId;
  }
}
