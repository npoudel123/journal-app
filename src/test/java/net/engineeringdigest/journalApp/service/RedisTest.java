package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    //We use this just to check if redis connection is working or not. So for now we disabled
    @Disabled
    @Test
    public void testSendEmail(){
        redisTemplate.opsForValue().set("email", "npoudel6789@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        int a =1;


    }
}
