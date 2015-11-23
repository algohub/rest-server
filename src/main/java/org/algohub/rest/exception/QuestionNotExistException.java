package org.algohub.rest.exception;

public class QuestionNotExistException extends Exception {
  private final String questionId;

  public QuestionNotExistException(final String questionId) {
    super(String.format("Question with id=%s does not exist", questionId));
    this.questionId = questionId;
  }
}
