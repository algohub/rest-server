package org.algohub.rest.exception;

public class QuestionAlreadyExistException extends Exception {
  private final String questionId;

  public QuestionAlreadyExistException(final String questionId) {
    super(String.format("Question with id=%s already exists", questionId));
    this.questionId = questionId;
  }
}
