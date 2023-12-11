package com.chx.chat.netty.service;

import com.chx.chat.netty.entity.Message;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

@Service
public interface WebSocketService {
    void put(Long uid, Channel channel);


    void send(Message message);

    Integer size();

    String getOffLineMessage(Long uid) ;

    boolean containsKey(Long uid);

    void del200Message(String msgId);

    boolean containsMessageKey(String msgId);


    Message getMessage(String msgId);
}
