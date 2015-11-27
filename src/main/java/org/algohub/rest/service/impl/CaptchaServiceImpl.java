package org.algohub.rest.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.algohub.rest.service.CaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

@Service
public class CaptchaServiceImpl implements CaptchaService {
  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper om = new ObjectMapper();

  @Value("${algohub.recaptcha.secret}")
  private String recaptchaSecret;

  private boolean valiateReCaptcha(String response, String remoteip) {
    if (response == null || response.isEmpty()) return false;
    if (remoteip == null || remoteip.isEmpty()) return false;

    String url = "https://www.google.com/recaptcha/api/siteverify";
    String charset = java.nio.charset.StandardCharsets.UTF_8.name();

    try {
      final String query = String.format("secret=%s&response=%s&remoteip=%s",
          URLEncoder.encode(recaptchaSecret, charset),
          URLEncoder.encode(response, charset),
          URLEncoder.encode(remoteip, charset));
      final String tmp = restTemplate.getForObject(new URI(url + "?" + query), String.class);
      final ObjectNode root = (ObjectNode) om.readTree(tmp);
      return root.get("success").asBoolean();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return false;
  }

  //TODO: sweet captcha
  private boolean validateSweetCaptcha() {
    return true;
  }

  public boolean validateCaptcha(ObjectNode userResponse) {
//    return true;
    if (userResponse == null && userResponse.size() == 0) return false;

    final String vendor = userResponse.get("vendor").asText();
    switch (vendor) {
      case "recaptcha": {
        final String response = userResponse.get("response").asText();
        final String remoteip = userResponse.get("remoteip").asText();

        return valiateReCaptcha(response, remoteip);
      }
      case "sweetcaptcha":
        return validateSweetCaptcha();
      default:
        return false;
    }
  }
}
