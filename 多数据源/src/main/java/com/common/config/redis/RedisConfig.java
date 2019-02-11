package com.common.config.redis;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置工具类
 * 
 * @author zjk
 * 2017年12月5日 下午5:59:53
 */
@Configuration
@PropertySource(value = "classpath:redis.properties")
@EnableCaching // 启用缓存，这个注解很重要；
public class RedisConfig extends CachingConfigurerSupport { // 继承CachingConfigurerSupport，为了自定义生成KEY的策略。可以不继承。

	private static Logger log = LoggerFactory.getLogger(RedisConfig.class);

	@Configuration
	static class LocalConfiguration {
		@Value("${spring.redis.host}")
		private String host;
		@Value("${spring.redis.port}")
		private int port;
		@Value("${spring.redis.password}")
		private String password;
		@Value("${spring.redis.timeout}")
		private int timeout;
		@Value("${spring.redis.pool.max-active}")
		private int maxActive;
		@Value("${spring.redis.pool.max-wait}")
		private int maxWait;
		@Value("${spring.redis.pool.max-idle}")
		private int maxIdl;
		@Value("${spring.redis.pool.min-idle}")
		private int minIdl;

		@Bean
		public JedisConnectionFactory redisConnectionFactory() {
			JedisConnectionFactory factory = new JedisConnectionFactory();
			factory.setHostName(host);
			factory.setPort(port);
			factory.setPassword(password);
			factory.setTimeout(timeout); // 设置连接超时时间
			JedisPoolConfig jpc = new JedisPoolConfig();
			jpc.setMaxIdle(maxIdl);
			jpc.setMinIdle(minIdl);
			jpc.setMaxTotal(maxActive);
			jpc.setMaxWaitMillis(maxWait);
			factory.setPoolConfig(jpc);
			return factory;
		}
		
		@Bean
		public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
			StringRedisTemplate template = new StringRedisTemplate(factory);
			setSerializer(template); // 设置序列化工具，这样ReportBean不需要实现Serializable接口
			template.afterPropertiesSet();
			return template;
		}
		
		/*
		 * 缓存管理器
		 * spring-cache使用一个CacheManager来进行管理缓存，为了使用我们的redis作为缓存源，需要向spring注册一个CacheManager
		 */
		@Bean
		public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
			RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
			//log.info("redis 默认过期时间 {} 秒", 100);
			//cacheManager.setDefaultExpiration(100); // 设置key-value默认过期时间
			return cacheManager;
		}
		
		//序列化工具
		private void setSerializer(StringRedisTemplate template) {
			Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
					Object.class);
			ObjectMapper om = new ObjectMapper();
			om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			jackson2JsonRedisSerializer.setObjectMapper(om);
			template.setValueSerializer(jackson2JsonRedisSerializer);
		}
	}

	/**
     * @description 自定义的缓存key的生成策略</br>
     *              若想使用这个key</br>
     *              只需要讲注解上keyGenerator的值设置为keyGenerator即可</br>
     * @return 自定义策略生成的key,此方法将会根据类名+方法名+所有参数的值生成唯一的一个key
     */
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		log.info("RedisCacheConfig.keyGenerator()");
		return new KeyGenerator() {
			@Override
			public Object generate(Object o, Method method, Object... objects) {
				StringBuilder sb = new StringBuilder();
				sb.append(o.getClass().getName());
				sb.append(method.getName());
				for (Object obj : objects) {
					sb.append(obj.toString());
				}
				log.info("keyGenerator=" + sb.toString());
				return sb.toString();
			}
		};
	}

}
