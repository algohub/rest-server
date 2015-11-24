package org.algohub.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.algohub.rest.domain.User;
import org.algohub.rest.dto.ErrorMessage;
import org.algohub.rest.dto.UserRegistration;
import org.algohub.rest.exception.InvalidTokenException;
import org.algohub.rest.exception.UserAlreadyExistException;
import org.algohub.rest.model.AuthenticationResponse;
import org.algohub.rest.security.TokenUtils;
import org.algohub.rest.service.CaptchaService;
import org.algohub.rest.service.MailService;
import org.algohub.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class RegistrationController {
  public final static String REGISTRATION_KEY = "register:";

  private ObjectMapper om = new ObjectMapper();
  @Autowired
  private CaptchaService captchaService;
  @Autowired
  private MailService mailService;
  @Autowired
  private TokenUtils tokenUtils;
  @Autowired
  ValueOperations<String, String> valueOps;
  @Autowired
  UserService userService;
  @Autowired
  private AuthenticationManager authenticationManager;


  @RequestMapping(method = RequestMethod.POST, value = "/register")
  public ObjectNode register(@NotNull @Valid UserRegistration userRegistration)
      throws JsonProcessingException {
    boolean captcha = captchaService.validateCaptcha(userRegistration.getCaptcha());
    if (captcha) {
      final User user = userRegistration.getUser();
      final UserDetails userDetails = user.toUserDetails();
      final String code = tokenUtils.generateToken(userDetails);
      valueOps.set(REGISTRATION_KEY + user.getUsername(), om.writeValueAsString(user), 15, TimeUnit.MINUTES);
      mailService.sendActivationMail(user.getUsername(), user.getEmail(), code);
    }
    final ObjectNode result = om.createObjectNode();
    result.put("success", captcha);
    return result;
  }

  @RequestMapping(method = RequestMethod.GET, value="/activate")
  public ResponseEntity<?> activate(@RequestParam("token") @NotNull String token)
      throws IOException, InvalidTokenException, UserAlreadyExistException {
    final String username = tokenUtils.getUsernameFromToken(token);
    final String json = valueOps.get(username);
    if (json == null) {  // expired
      final ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(HttpStatus.NOT_FOUND);
      return bodyBuilder.body(new ErrorMessage("Your registration link has expired"));
    }

    final User user = om.readValue(json, User.class);
    final UserDetails userDetails = user.toUserDetails();

    if (tokenUtils.validateToken(token, userDetails)) {
        userService.create(user);

        // Perform the authentication
        Authentication authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                username, user.getPasswordHash()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Return the token
        return ResponseEntity.ok(new AuthenticationResponse(token));
    } else {
      throw new InvalidTokenException(token);
    }
  }

  @Bean
  ValueOperations<String, String> valueOps(StringRedisTemplate redisTemplate) {
    return redisTemplate.opsForValue();
  }
}
