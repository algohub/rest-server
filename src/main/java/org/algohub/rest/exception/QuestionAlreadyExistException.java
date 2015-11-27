package org.algohub.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public final class QuestionAlreadyExistException extends Exception {
  private final String questionId;

  public QuestionAlreadyExistException(final String questionId) {
    super(String.format("Question with id=%s already exists", questionId));
    this.questionId = questionId;
  }
}
