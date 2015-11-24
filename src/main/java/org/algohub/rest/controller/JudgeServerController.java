package org.algohub.rest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class JudgeServerController {
  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${algohub.judge.url}")
  private String judgeUrl;

  @RequestMapping(method = RequestMethod.POST, value = "/question/judge/java/{id}")
  public String judgeJava(@PathVariable("id") String id, @RequestBody String userCode) {
    return restTemplate.postForObject(judgeUrl + "/question/judge/java/" + id, userCode, String.class);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/question/judge/cpp/{id}")
  public String judgeCpp(@PathVariable("id") String id, @RequestBody String userCode) {
    return restTemplate.postForObject(judgeUrl + "/question/judge/java/" + id, userCode, String.class);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/question/judge/python/{id}")
  public String judgePython(@PathVariable("id") String id, @RequestBody String userCode) {
    return restTemplate.postForObject(judgeUrl + "/question/judge/java/" + id, userCode, String.class);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/question/judge/ruby/{id}")
  public String judgeRuby(@PathVariable("id") String id, @RequestBody String userCode) {
    return restTemplate.postForObject(judgeUrl + "/question/judge/java/" + id, userCode, String.class);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/question/judge")
  public String judge(@RequestBody final String answer) {
    return restTemplate.postForObject(judgeUrl + "/question/judge/" , answer, String.class);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/submission/check/{id}")
  public String check(@PathVariable("id") long id) {
    return restTemplate.getForObject(judgeUrl + "/submission/check/" + id, String.class);
  }
}
