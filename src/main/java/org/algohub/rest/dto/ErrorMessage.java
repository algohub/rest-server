package org.algohub.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {
  private String error;   // error message
  @JsonProperty("exception_class")
  private String exceptionClass;  // exceptionClass class name

  public ErrorMessage() {}

  public ErrorMessage(String error) {
    this.error = error;
  }

  public ErrorMessage(String error, String exceptionClass) {
    this.error = error;
    this.exceptionClass = exceptionClass;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getExceptionClass() {
    return exceptionClass;
  }

  public void setExceptionClass(String exceptionClass) {
    this.exceptionClass = exceptionClass;
  }
}
