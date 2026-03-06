package net.engineeringdigest.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //Here redis value must be string, so to store object we have to use Jackson2JsonRedisSerializer
//        redisTemplate.setValueSerializer(new StringRedisSerializer());

        //Here we store object and convert to json
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        return  redisTemplate;
    }
}
