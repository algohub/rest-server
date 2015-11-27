package org.algohub.rest.controller;

import org.algohub.rest.dto.ErrorMessage;
import org.algohub.rest.service.TokenService;
import org.algohub.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class RestServerController {

  @Autowired
  private TokenService tokenService;

  //TODO: always get 302 Found if use "/logout"
  @RequestMapping(method = RequestMethod.GET, value = "/signout")
  public ErrorMessage logout(@RequestHeader("X-Auth-Token") String token) {
    tokenService.revoke(token);
    return new ErrorMessage(true, "You have successfully logged out");
  }
}
