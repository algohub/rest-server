package org.algohub.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.algohub.rest.domain.User;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserRegistration implements Serializable {
  private static final long serialVersionUID = 60000L;
  @NotNull
  @Size(min = 4, max = 16)
  @Pattern(regexp = "[a-z0-9_-]{4,16}")
  private String username;

  @NotNull
  @Size(min = 7, max = 20)
  @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{7,}")
  private String password;

  @NotNull
  @Size(min = 5, max = 255)
  @Email
  private String email;

  @NotNull
  private User.Gender gender;
  @NotNull
  private User.Occupation occupation;

  @NotNull
  private ObjectNode captcha;

  public UserRegistration() {}

  public UserRegistration(String username, String password, String email, User.Gender gender,
      User.Occupation occupation, ObjectNode captcha) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.gender = gender;
    this.occupation = occupation;
    this.captcha = captcha;
  }

  public UserDetails toUserDetails() {
    return new org.springframework.security.core.userdetails.User(username, password,
        AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
  }

  public User toUser() {
    return new User(username, password, email, gender, occupation);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User.Gender getGender() {
    return gender;
  }

  public void setGender(User.Gender gender) {
    this.gender = gender;
  }

  public User.Occupation getOccupation() {
    return occupation;
  }

  public void setOccupation(User.Occupation occupation) {
    this.occupation = occupation;
  }

  public ObjectNode getCaptcha() {
    return captcha;
  }

  public void setCaptcha(ObjectNode captcha) {
    this.captcha = captcha;
  }
}
