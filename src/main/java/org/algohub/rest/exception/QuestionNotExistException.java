package org.algohub.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public final class QuestionNotExistException extends Exception {
  private final String questionId;

  public QuestionNotExistException(final String questionId) {
    super(String.format("Question with id=%s does not exist", questionId));
    this.questionId = questionId;
  }
}
