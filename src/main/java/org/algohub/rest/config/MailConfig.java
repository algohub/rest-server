package org.algohub.rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {
  @Value("${spring.mail.host}")
  private String host;
  @Value("${spring.mail.port}")
  private int port;
  @Value("${spring.mail.username}")
  private String username;
  @Value("${spring.mail.password}")
  private String password;
  @Value("${spring.mail.protocol}")
  private String protocol;

  @Value("${spring.mail.smtps.auth}")
  private boolean auth;
  @Value("${spring.mail.smtp.ssl.enable}")
  private boolean sslEnable;
  @Value("${spring.mail.from}")
  private String from;
  @Value("${spring.mail.transport.protocol}")
  private String transportProtocol;


  @Bean
  public JavaMailSender javaMailSender() {
    final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);
    mailSender.setProtocol(protocol);
    mailSender.setDefaultEncoding("UTF-8");

    final Properties mailProperties = new Properties();
    mailProperties.put("mail.smtps.auth", auth);
    mailProperties.put("mail.smtp.ssl.enable", sslEnable);
    mailProperties.put("mail.from", from);
    mailProperties.put("mail.transport.protocol", transportProtocol);
    mailSender.setJavaMailProperties(mailProperties);

    return mailSender;
  }
}
