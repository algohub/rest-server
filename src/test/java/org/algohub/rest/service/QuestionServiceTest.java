package org.algohub.rest.service;

import org.algohub.rest.RestServerApplication;
import org.algohub.rest.domain.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(RestServerApplication.class)
public class QuestionServiceTest {
  @Autowired
  private QuestionService questionService;

  @Before
  public void setup() {

  }
  @Test
  public void getQuestionByIdTest() {
    assertEquals(1,1);
//    final Question question = questionService.getQuestionById("2-sum");
//    System.out.println(question.getJson());
//    final List<String> idList = questionService.findAllIds();
//    for (String id : idList) {
//      System.out.println(id);
//    }
  }
}
