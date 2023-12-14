package com.chx.chat.mq;

import com.chx.chat.constant.RabbitMQCodeConstant;
import com.chx.chat.entity.SystemNotifyInfoEntity;
import com.chx.chat.service.MessageInfoService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月14日
 */
@Slf4j
@Service
@RabbitListener(queues = RabbitMQCodeConstant.CHAT_DATA_MESSAGE_QUEUE)
public class MessagePersistenceMQ {
    @Autowired
    private MessageInfoService messageInfoService;

    @RabbitHandler
    public void orderTransactionalBack(String queue, Message message, Channel channel) throws IOException {
        log.info("收到需持久化的消息: " + queue.toString());
        try{
            messageInfoService.messagePersistence(queue);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }
}
