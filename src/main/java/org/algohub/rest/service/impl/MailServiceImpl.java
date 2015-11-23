package org.algohub.rest.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.algohub.rest.service.MailService;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;


@Service("mailService")
@Transactional
public class MailServiceImpl implements MailService {
  @Autowired
  private JavaMailSender javaMailSender;
  @Autowired
  private VelocityEngine velocityEngine;

  private static final String defaultEncoding ="UTF-8";

  private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);


  public void sendActivationMail(String username, String email, String code) {
    String templatePath = "/template/verify_email.vm";
    Map<String, Object> model = new HashMap<>();
    model.put("username", username);
    model.put("code", code);

    String subject = "Activate Your Account";
    //String body = this.getMailContent(templatePath, model);
    String body = "Registration activation";
    this.sendMail(email, subject, body);
  }

  private String getMailContent(String templateLocation, Map<String, Object> model) {
    return VelocityEngineUtils.
        mergeTemplateIntoString(velocityEngine, templateLocation, defaultEncoding, model);
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
}
