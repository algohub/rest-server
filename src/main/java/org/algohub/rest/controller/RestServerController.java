package org.algohub.rest.controller;

import org.algohub.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RestServerController {
  private final UserService userService;

  @Autowired
  public RestServerController(final UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void create() {
  }
}
