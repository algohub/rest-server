package org.algohub.rest.controller;

import org.algohub.rest.domain.User;
import org.algohub.rest.exception.UserNotExistException;
import org.algohub.rest.service.TokenService;
import org.algohub.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private TokenService tokenService;

  @RequestMapping(method = RequestMethod.GET, value = "{username}")
  public User get(@PathVariable final String username) throws UserNotExistException {
    return userService.getUserByName(username);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "{username}")
  public User update(@PathVariable final String username, @RequestBody @Valid final User user) {
    return userService.update(user);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "{username}")
  public void delete(@PathVariable final String username) throws UserNotExistException {
    final User user = userService.getUserByName(username);
    userService.delete(user);
    tokenService.revokeAll(user.getUsername());
  }
}
