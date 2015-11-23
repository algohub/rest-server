package org.algohub.rest.integration.config;

import java.util.concurrent.TimeUnit;

import org.algohub.rest.util.TestApiConfig;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContext {

  @Bean
  public EmbeddedServletContainerFactory servletContainer() {
    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
    factory.setPort(TestApiConfig.PORT);
    factory.setSessionTimeout(10, TimeUnit.MINUTES);
    return factory;
  }

}
