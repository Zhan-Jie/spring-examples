package zhanj;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                String value;
                Cacheable cacheable ;
                CachePut cachePut;
                CacheEvict cacheEvict;

                if ((cacheable = method.getAnnotation(Cacheable.class))!= null) {
                    value = cacheable.value()[0];
                } else if ((cachePut = method.getAnnotation(CachePut.class)) != null) {
                    value = cachePut.value()[0];
                } else if ((cacheEvict = method.getAnnotation(CacheEvict.class)) != null) {
                    value = cacheEvict.value()[0];
                } else {
                    value = "";
                }
                StringBuilder sb = new StringBuilder();
                sb.append(value);
                for (Object obj : params) {
                    sb.append(":").append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(RedisTemplate redisTemplate) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        return configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
        .entryTtl(Duration.ofMinutes(5));
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory, RedisCacheConfiguration redisCacheConfiguration) {
        return RedisCacheManager.builder(factory).cacheDefaults(redisCacheConfiguration).build();
    }
}
