package org.algohub.rest.service;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface CaptchaService {
  boolean validateCaptcha(ObjectNode userResponse);
}
