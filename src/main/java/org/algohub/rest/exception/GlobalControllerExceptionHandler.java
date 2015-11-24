package org.algohub.rest.exception;

import org.algohub.rest.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleUserNotExistException(UserNotExistException e) {
    return new ErrorMessage(e.getMessage(), e.getClass().getName());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorMessage handleUserAlreadyExistException(UserAlreadyExistException e) {
    return new ErrorMessage(e.getMessage(), e.getClass().getName());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleQuestionNotExistException(QuestionNotExistException e) {
    return new ErrorMessage(e.getMessage(), e.getClass().getName());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorMessage handleQuestionAlreadyExistException(QuestionAlreadyExistException e) {
    return new ErrorMessage(e.getMessage(), e.getClass().getName());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage handleIOException(IOException e) {
    return new ErrorMessage(e.getMessage(), e.getClass().getName());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  public ErrorMessage handleInvalidTokenException(InvalidTokenException e) {
    return new ErrorMessage(e.getMessage(), e.getClass().getName());
  }
}
