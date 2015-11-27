package org.algohub.rest.service.impl;

import org.algohub.rest.domain.Question;
import org.algohub.rest.exception.QuestionAlreadyExistException;
import org.algohub.rest.exception.QuestionNotExistException;
import org.algohub.rest.repository.QuestionRepository;
import org.algohub.rest.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Service
public class QuestionServiceImpl implements QuestionService {
  private final QuestionRepository questionRepository;

  @Autowired
  public QuestionServiceImpl(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Transactional
  @Caching(evict = @CacheEvict("all-question-ids"), put = @CachePut(cacheNames = "question", key = "#id"))
  public @Valid Question create(@NotNull @Valid final Question question)
      throws QuestionAlreadyExistException {
    if (questionRepository.exists(question.getId())) {
      throw new QuestionAlreadyExistException(question.getId());
    } else {
      return questionRepository.save(question);
    }
  }

  @Cacheable(cacheNames = "question", key = "#id", unless = "#result == null")
  @Transactional(readOnly = true)
  public @Valid Question getQuestionById(@NotNull @Size(min = 3, max = 255) final String id) {
    return questionRepository.findOne(id);
  }

  @Transactional
  @Caching(evict = @CacheEvict("all-question-ids"), put = @CachePut(cacheNames = "question", key = "#id"))
  public @Valid Question update(@NotNull @Valid final Question question)
      throws QuestionNotExistException {
    if (questionRepository.exists(question.getId())) {
      return questionRepository.save(question);
    } else {
      throw new QuestionNotExistException(question.getId());

    }
  }

  @Transactional
  @Caching(evict = { @CacheEvict("all-question-ids"), @CacheEvict(cacheNames = "question", key = "#id") })
  public void delete(@NotNull @Size(min = 3, max = 255) final String id) {
    questionRepository.delete(id);
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = "all-question-ids", unless = "#result != null and #result.size() == 0")
  public List<String> findAllIds() {
    final List<String> idList = new ArrayList<>();
    final Iterable<Question> questions = questionRepository.findAll();
    for (final Question question : questions) {
      idList.add(question.getId());
    }
    return idList;
  }
}
