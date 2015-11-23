package org.algohub.rest.service;

import org.algohub.rest.domain.Question;
import org.algohub.rest.exception.QuestionAlreadyExistException;
import org.algohub.rest.exception.QuestionNotExistException;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
  Question create(final Question question) throws QuestionAlreadyExistException;

  Question getQuestionById(String id);

  Question update(final Question question) throws QuestionNotExistException;

  void delete(final String id);

  List<String> findAllIds();
}
