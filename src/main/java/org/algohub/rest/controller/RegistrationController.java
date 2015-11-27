package org.algohub.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.algohub.rest.domain.User;
import org.algohub.rest.dto.ErrorMessage;
import org.algohub.rest.dto.UserRegistration;
import org.algohub.rest.exception.InvalidCaptchaException;
import org.algohub.rest.exception.InvalidTokenException;
import org.algohub.rest.security.TokenUtils;
import org.algohub.rest.service.CaptchaService;
import org.algohub.rest.service.MailService;
import org.algohub.rest.service.TokenService;
import org.algohub.rest.service.UserService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class RegistrationController {
  public final static String REGISTRATION_KEY = "algohub:register:";

  private ObjectMapper om = new ObjectMapper();
  @Autowired
  private CaptchaService captchaService;
  @Autowired
  private MailService mailService;
  @Autowired
  private TokenUtils tokenUtils;
  @Autowired
  private TokenService tokenService;
  @Autowired
  private StringRedisTemplate redisTemplate;
  @Autowired
  UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;


  @RequestMapping(method = RequestMethod.POST, value = "/register")
  public ResponseEntity<?> register(@RequestBody @Valid UserRegistration userRegistration)
      throws JsonProcessingException, InvalidCaptchaException {
    userRegistration.setPassword(passwordEncoder.encode(userRegistration.getPassword()));

    boolean captchaIsValid = captchaService.validateCaptcha(userRegistration.getCaptcha());
    if (!captchaIsValid) {
      throw new InvalidCaptchaException();
    }
    final String username =  userRegistration.getUsername();
    final String email = userRegistration.getEmail();
    // detect whether the user exists
    {
      String json = redisTemplate.opsForValue().get(REGISTRATION_KEY + username);
      User user = userService.getUserByName(username);
      if (json != null || user != null) {
        final ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.CONFLICT);
        return bodyBuilder.body(new ErrorMessage(false, "The username " + username +
            " already exists"));
      }

      json = redisTemplate.opsForValue().get(REGISTRATION_KEY + email);
      user = userService.getUserByEmail(email);
      if (json != null || user != null) {
        final ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.CONFLICT);
        return bodyBuilder.body(new ErrorMessage(false, "The Email address " + email +
            " is in use"));
      }
    }

    final UserDetails userDetails = userRegistration.toUserDetails();
    final String code = tokenUtils.generateToken(userDetails);

    redisTemplate.opsForValue().set(REGISTRATION_KEY + username, om.writeValueAsString(userRegistration), 15,
        TimeUnit.MINUTES);
    redisTemplate.opsForValue().set(REGISTRATION_KEY + email, "1", 15, TimeUnit.MINUTES);
    mailService.sendAccountActivationEmail(username, userRegistration.getEmail(), code);

    return ResponseEntity.ok(new ErrorMessage(true, "You're almost there, "
        + "please check your email and activate your account."));
  }

  @RequestMapping(method = RequestMethod.GET, value="/activate")
  public ResponseEntity<?> activate(@RequestParam("code") @NotEmpty String token)
      throws IOException, InvalidTokenException {
    final String username = tokenUtils.getUsernameFromToken(token);
    final String json = redisTemplate.opsForValue().get(REGISTRATION_KEY + username);
    if (json == null) {  // expired
      final ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.NOT_FOUND);
      return bodyBuilder.body(new ErrorMessage(false, "Your registration link is expired"));
    }

    final UserRegistration userRegistration = om.readValue(json, UserRegistration.class);
    final User user = userRegistration.toUser();
    final UserDetails userDetails = user.toUserDetails();

    if (!tokenService.validate(token, userDetails)) {
      throw new InvalidTokenException(token);
    }

    userService.create(user);
    redisTemplate.delete(REGISTRATION_KEY + username);
    redisTemplate.delete(REGISTRATION_KEY + userRegistration.getEmail());

    final ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.CREATED);
    return bodyBuilder.body(new ErrorMessage(true, "Congratulations! Your account is activated now."));
  }
}
