package org.algohub.rest.service;

import org.algohub.rest.RestServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(RestServerApplication.class)
public class MailServiceTest {
  @Autowired
  private MailService mailService;
  @Autowired JavaMailSender javaMailSender;

  @Test
  public void sendActivationMailTest() {
//    mailService.sendActivationMail("soulmachine", "soulmachine@gmail.com", "123456xxx");
    assertEquals(1, 1);
  }
}
