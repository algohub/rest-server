package org.algohub.rest.dto;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordReset implements Serializable {
  private static final long serialVersionUID = 70000L;

  @NotNull
  @Size(min = 4, max = 16)
  @Pattern(regexp = "[a-z0-9_-]{4,16}")
  private String username;

  @NotNull
  private ObjectNode captcha;

  public PasswordReset() {}

  public PasswordReset(String username, ObjectNode captcha) {
    this.username = username;
    this.captcha = captcha;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public ObjectNode getCaptcha() {
    return captcha;
  }

  public void setCaptcha(ObjectNode captcha) {
    this.captcha = captcha;
  }
}
