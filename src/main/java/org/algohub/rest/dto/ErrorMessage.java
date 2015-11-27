package org.algohub.rest.dto;


public class ErrorMessage {
  private boolean success;
  private String message;
  private String exception;

  public ErrorMessage() {}

  public ErrorMessage(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public ErrorMessage(String message, String exception) {
    this.success = false;
    this.message = message;
    this.exception = exception;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }
}
