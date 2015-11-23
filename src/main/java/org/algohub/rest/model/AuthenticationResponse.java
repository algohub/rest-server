package org.algohub.rest.model;


import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
  private static final long serialVersionUID = -6624726180748515507L;
  private String token;

  public AuthenticationResponse() {
    // No args constructor for Jackson
  }

  public AuthenticationResponse(String token) {
    this.setToken(token);
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "{\"token\":" + token + "}";
  }
}
