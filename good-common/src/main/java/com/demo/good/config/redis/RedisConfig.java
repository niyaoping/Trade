package com.demo.good.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * redis配置类
 */
@Configuration
public class RedisConfig {
	/**
	 * redis配置属性读取
	 */
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.database}")
	private int database;
	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;
	@Value("${spring.redis.jedis.pool.max-wait}")
	private long maxWaitMillis;
	@Value("${spring.redis.jedis.pool.max-active}")
	private int maxActive;


	/**
	 * JedisPoolConfig配置
	 * @return
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		System.out.println("初始化JedisPoolConfig");
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxActive);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setMaxIdle(maxIdle);
		return jedisPoolConfig;
	}

	/**
	 * 注入RedisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
		System.out.println("初始化JedisConnectionFactory");
		// JedisConnectionFactory配置hsot、database、password等参数
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		redisStandaloneConfiguration.setDatabase(database);
		// JedisConnectionFactory配置jedisPoolConfig
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolConfigBuilder =
				(JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
		jedisPoolConfigBuilder.poolConfig(jedisPoolConfig);
		return new JedisConnectionFactory(redisStandaloneConfiguration);

	}
	/**
	 * 重新定义RedisTemplate的序列化方式 用于基本操作
	 */
	@Bean
	@Primary
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		//使用fastjson序列化
		FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
		// value值的序列化采用fastJsonRedisSerializer
		template.setValueSerializer(fastJsonRedisSerializer);
		template.setHashValueSerializer(fastJsonRedisSerializer);
		// key的序列化采用StringRedisSerializer
		template.setKeySerializer(redisSerializer);
		template.setHashKeySerializer(redisSerializer);
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	/**
	 * 重新定义RedisTemplate的序列化方式 用于shiro redis操作
	 */
	@Bean
	@Autowired
	public RedisTemplate<Object, Object> redisTemplateObj(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		//使用默认序列化方式
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		template.setValueSerializer(jdkSerializationRedisSerializer);
		template.setHashValueSerializer(jdkSerializationRedisSerializer);
		template.setKeySerializer(redisSerializer);
		template.setHashKeySerializer(redisSerializer);
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	/**
	 * 重新定义缓存管理器的序列化方式
	 */
	@Bean
	@Autowired
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		//使用fastjson序列化
		FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
		// 生成一个默认配置，通过config对象即可对缓存进行自定义配置
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		config = config.entryTtl(Duration.ofMinutes(30)) // 设置缓存的默认过期时间30分钟，也是使用Duration设置
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
				.disableCachingNullValues(); // 不缓存空值
		// 使用自定义的缓存配置初始化一个cacheManager
		RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(config)
				.build();
		return cacheManager;
	}

}