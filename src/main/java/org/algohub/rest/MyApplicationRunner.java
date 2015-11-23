package org.algohub.rest;

import org.algohub.rest.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyApplicationRunner implements ApplicationRunner{
  @Autowired
  private QuestionService questionService;
  public void run(ApplicationArguments args) throws Exception {
    // pull data from database and cache to Redis
    final List<String> idList = questionService.findAllIds();
    for (final String id : idList) {
      questionService.getQuestionById(id);
    }
  }
}
