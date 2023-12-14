package com.chx.chat;

import com.chx.chat.config.redis.RedisService;
import com.mysql.cj.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class BiubiuChatApplicationTests {

    @Autowired
    private RedisService redisService;


    @Test
    void contextLoads() {
        redisService.setCacheObject("moc","12312313", 5l, TimeUnit.SECONDS);
    }

}
