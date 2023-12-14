package com.chx.chat.config;

import com.chx.chat.netty.entity.Message;
import com.chx.chat.netty.entity.MessageContent;
import com.chx.chat.netty.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.util.Json;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月10日
 */
public class Programmer {
    private static LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    public static void main(String[] args) throws JsonProcessingException {
        Message message = new Message();
        Message message1 = new Message();
        message1.setMsgId("12321");
        message.setMsgId("0233");
        MessageContent messageContent = new MessageContent(1l,2l,"123",null);
        MessageContent messageContent1 = new MessageContent(1l,2l,"123",null);
        message1.setMsg(messageContent1);
        message.setMsg(messageContent);
        queue.add(message);
        queue.add(message1);
        String json = JsonUtil.message(queue);
        System.out.println(json);
        LinkedBlockingQueue<Message> parse = JsonUtil.blockingQueueParse(json);
        for (Message message2 : parse) {
            System.out.println(message2.toString());
        }
        System.out.println(parse.toString());
    }
}
