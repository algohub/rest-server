package org.algohub.rest.service.impl;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.algohub.rest.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Service("mailService")
public class MailServiceImpl implements MailService {
  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  @Qualifier("verifyEmailTemplate")
  private Template verifyEmailTemplate;

  @Autowired
  @Qualifier("passwordResetEmailTemplate")
  private Template passwordResetEmailTemplate;

  private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);


  public void sendAccountActivationEmail(String username, String email, String code) {
    final String subject = "Activate Your Algohub Account";

    Map<String, String> data = new HashMap<>();
    data.put("username", username);
    data.put("code", code);
    data.put("subject", subject);

    String body = verifyEmailTemplate.execute(data);
    this.sendMail(email, subject, body);
  }

  public void sendPasswordResetEmail(String username, String email, String code) {
    final String subject = "Reset Your Algohub Password";

    Map<String, String> data = new HashMap<>();
    data.put("username", username);
    data.put("code", code);
    data.put("subject", subject);

    String body = passwordResetEmailTemplate.execute(data);
    this.sendMail(email, subject, body);
  }

  private void sendMail(final String recipient, final String subject, final String body) {
    MimeMessagePreparator preparator = (MimeMessage mimeMessage) -> {
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
      message.setTo(recipient);
      message.setReplyTo(recipient);
      message.setSubject(subject);
      message.setText(body, true);
    };
    javaMailSender.send(preparator);
    LOGGER.info(String.format("An Email{Recipient: %s, Subject: %s} has been sent.",
        recipient, subject));
  }

  @Bean
  @Qualifier("verifyEmailTemplate")
  Template createVerifyEmailTemplate() {
    return createTemplate("/template/account_activation_email.html");
  }

  @Bean
  @Qualifier("passwordResetEmailTemplate")
  Template createPasswordResetEmailTemplate() {
    return createTemplate("/template/password_reset_email.html");
  }

  private static Template createTemplate(String resourceAbsolutePath) {
    final InputStream is = MailServiceImpl.class.getResourceAsStream(resourceAbsolutePath);
    java.util.Scanner scanner = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
    final String text = scanner.hasNext() ? scanner.next() : "";
    return Mustache.compiler().compile(text);
  }
}
