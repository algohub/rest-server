package org.algohub.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class InvalidCaptchaException extends Exception {
  public InvalidCaptchaException() {
    super("The captcha entered is not correct");
  }
}
