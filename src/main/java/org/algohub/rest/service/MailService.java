package org.algohub.rest.service;


public interface MailService {
  void sendAccountActivationEmail(String username, String email, String code);
  void sendPasswordResetEmail(String username, String email, String code);
}
