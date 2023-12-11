package com.chx.chat;

import com.chx.chat.config.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BiubiuChatApplicationTests {

    @Autowired
    private RedisService redisService;


    @Test
    void contextLoads() {
        redisService.setCacheObject("moc","12312313");
    }

}
