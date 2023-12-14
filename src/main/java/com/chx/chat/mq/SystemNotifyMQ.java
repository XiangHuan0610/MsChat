package com.chx.chat.mq;

import com.chx.chat.constant.RabbitMQCodeConstant;
import com.chx.chat.entity.SystemNotifyInfoEntity;
import com.chx.chat.netty.service.WebSocketService;
import com.chx.chat.service.SystemNotifyInfoService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
@Slf4j
@Service
@RabbitListener(queues = RabbitMQCodeConstant.CHAT_NOTIFY_INFO_QUEUE)
public class SystemNotifyMQ {

    @Autowired
    private SystemNotifyInfoService notifyInfoService;

    @RabbitHandler
    public void orderTransactionalBack(SystemNotifyInfoEntity notifyInfoEntity, Message message, Channel channel) throws IOException {
        log.info("收到添加好友通知: " + notifyInfoEntity.toString());
        try{

            notifyInfoService.addNotify(notifyInfoEntity);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }

}
