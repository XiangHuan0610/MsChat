package com.chx.chat.mq;

import com.chx.chat.constant.RabbitMQCodeConstant;
import com.chx.chat.netty.service.WebSocketService;
import com.chx.chat.utils.Query;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.FreeMarkerConfigurerBeanDefinitionParser;

import java.io.IOException;
import java.io.Serial;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
@Slf4j
@Service
@RabbitListener(queues = {RabbitMQCodeConstant.CHAT_RELIABLE_MESSAGE_QUEUE})
public class UmsDelayMessageMQ {

    @Autowired
    private WebSocketService webSocketService;

    @RabbitHandler
    public void orderTransactionalBack(String msgId, Message message, Channel channel) throws IOException {
        log.info("收到消息ID: " + msgId);
        try{
            if (webSocketService.containsMessageKey(msgId)) {
                // 重发
                webSocketService.send(webSocketService.getMessage(msgId));
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }
}
