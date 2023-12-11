package com.chx.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author intel小陈
 * @date 2023年09月12日 10:51
 */
@Slf4j
@Configuration
public class RabbitMQReturnBackConfig implements RabbitTemplate.ReturnsCallback {

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("消息 {} 经交换机 {} 通过routingKey={} 路由到队列失败，失败code为：{}， 失败原因为：{}",
                new String(returned.getMessage().getBody()), returned.getExchange(), returned.getExchange(), returned.getReplyCode(), returned.getReplyText());
    }
}
