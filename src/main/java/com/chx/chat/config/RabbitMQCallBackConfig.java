package com.chx.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMQCallBackConfig implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId();

        String message = new String(correlationData.getReturned().getMessage().getBody());
        if (ack){
            log.info("交换机收到消息id为{}, 消息内容为{}", id, message);
        }else {
            log.info("交换机未收到消息id为{}, 消息内容为{}, 原因为{}", id, message, cause);
        }
    }

}