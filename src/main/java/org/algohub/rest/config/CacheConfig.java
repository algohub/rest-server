package org.algohub.rest.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
  @Value("${spring.redis.host}")
  private String host;
  @Value("${spring.redis.port}")
  private int port;
  @Value("${spring.redis.timeout}")
  private int timeout;

  @Bean
  public KeyGenerator keyGenerator(){
    return (Object target, Method method, Object... params) -> {
      StringBuilder sb = new StringBuilder();
      sb.append(target.getClass().getName());
      sb.append('-').append(method.getName());
      for (Object obj : params) {
        sb.append('-').append(obj.toString());
      }
      return sb.toString();
    };
  }

  @Bean
  public JedisConnectionFactory redisConnectionFactory() {
    JedisConnectionFactory factory = new JedisConnectionFactory();
    factory.setHostName(host);
    factory.setPort(port);
    factory.setTimeout(timeout);
    return factory;
  }

  @Bean
  public CacheManager cacheManager(RedisTemplate redisTemplate) {
    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
    // Number of seconds before expiration. Defaults to unlimited (0)
    cacheManager.setDefaultExpiration(3600);
    cacheManager.setUsePrefix(true);  // Use the cache names as a prefix appended to keys
    return cacheManager;
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory,
      RedisSerializer redisSerializer) {
    StringRedisTemplate template = new StringRedisTemplate(factory);
    template.setValueSerializer(redisSerializer);
    template.afterPropertiesSet();
    return template;
  }

  @Bean
  public RedisSerializer redisSerializer() {
    return new GenericJackson2JsonRedisSerializer();
  }
}
