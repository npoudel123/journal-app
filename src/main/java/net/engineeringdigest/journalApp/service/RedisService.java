package net.engineeringdigest.journalApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {


    private RedisTemplate redisTemplate;

    public RedisService(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public <T> T get(String key, Class<T> weatherReponseClass){
        try {
            Object obj = redisTemplate.opsForValue().get(key);

            if (obj == null) {
                return null;
            }
            //Converting obj to WeatherResponse Class
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(obj.toString(), weatherReponseClass);
        } catch (JsonProcessingException e) {
            log.error("Exception occure while getting redis", e);
            throw new RuntimeException(e);
        }

    }

    //here ttl is time, we set obj to key for ttl amout of time and TimeUnit.SECONDS make ttl to minutes(e.g if we pass 5, then it will be for 5 SECONDS)
    public void set(String key, Object obj, Long ttl){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(obj);
            redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error("Exception occure while setting redis", e);
            throw new RuntimeException(e);
        }

    }

}
