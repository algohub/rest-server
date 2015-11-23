package org.algohub.rest.dto;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.algohub.rest.domain.User;

import java.io.Serializable;


public class UserRegistration implements Serializable {
  private static final long serialVersionUID = 50000L;
  private User user;
  private ObjectNode captcha;

  public UserRegistration() {}

  public UserRegistration(User user, ObjectNode captcha) {
    this.user = user;
    this.captcha = captcha;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public ObjectNode getCaptcha() {
    return captcha;
  }

  public void setCaptcha(ObjectNode captcha) {
    this.captcha = captcha;
  }
}
