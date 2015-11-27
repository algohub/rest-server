package org.algohub.rest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public final class UserNotExistException extends Exception {

  public UserNotExistException(String message) {
    super(message);
  }
}
