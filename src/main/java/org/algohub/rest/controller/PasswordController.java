package org.algohub.rest.controller;


import org.algohub.rest.domain.User;
import org.algohub.rest.dto.ErrorMessage;
import org.algohub.rest.dto.PasswordReset;
import org.algohub.rest.exception.InvalidCaptchaException;
import org.algohub.rest.security.TokenUtils;
import org.algohub.rest.service.CaptchaService;
import org.algohub.rest.service.MailService;
import org.algohub.rest.service.TokenService;
import org.algohub.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/password")
public class PasswordController {
  @Autowired
  private UserService userService;
  @Autowired
  private CaptchaService captchaService;
  @Autowired
  private MailService mailService;
  @Autowired
  private TokenUtils tokenUtils;
  @Autowired
  private TokenService tokenService;

  @RequestMapping(method = RequestMethod.POST, value = "/reset")
  public ErrorMessage reset(@Valid PasswordReset passwordReset )
      throws InvalidCaptchaException {
    boolean captchaIsValid = captchaService.validateCaptcha(passwordReset.getCaptcha());
    if (!captchaIsValid) {
      throw new InvalidCaptchaException();
    }

    final User user = userService.getUserByName(passwordReset.getUsername());
    // 15 minutes
    final String code = tokenUtils.generateToken(user.toUserDetails(), 60 * 15);
    mailService.sendPasswordResetEmail(user.getUsername(), user.getEmail(), code);

    return new ErrorMessage(true, "You're almost there, "
        + "please check your email and reset your password.");
  }

  @RequestMapping(method = RequestMethod.POST, value = "/change")
  public ErrorMessage change(@NotNull @RequestBody String password) {
    final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
        SecurityContextHolder.getContext().getAuthentication();
    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    final String username = userDetails.getUsername();
    userService.changePassword(username, password);
    tokenService.revokeAll(username);
    return new ErrorMessage(true, "Your password has been successfully changed.");
  }
}
