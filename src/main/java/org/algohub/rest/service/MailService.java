package org.algohub.rest.service;


public interface MailService {
  void sendActivationMail(String username, String email, String code);
}
