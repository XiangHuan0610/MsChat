package com.chx.chat.config.redis;

import com.chx.chat.constant.SystemCodeConstant;
import com.chx.chat.netty.util.JsonUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public class KeyExpiredListener implements MessageListener {

    private final RedisTemplate<Object, Object> redisTemplate;


    @Autowired
    private RedisService redisService;

    public KeyExpiredListener(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody());
        if (expiredKey.contains(SystemCodeConstant.OFFLINE_USER_EXPIRATION)){
            Long uid = Long.valueOf(expiredKey.split(SystemCodeConstant.OFFLINE_USER_EXPIRATION)[1].charAt(0)+"");
            List<String> caches = redisService.getCacheList(SystemCodeConstant.OFFLINE_USER_KEY + uid);
            List<com.chx.chat.netty.entity.Message> messages = JsonUtil.messageCollectionParse(caches);
            // TODO 将离线消息添加到数据库

            redisService.deleteObject(SystemCodeConstant.OFFLINE_USER_KEY + uid);
        }
    }
}
