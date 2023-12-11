package com.chx.chat.constant;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
public class RabbitMQCodeConstant {
    private static final String CHAT_USER_EXCHANGE = "chat.user.exchange";

    public static final String CHAT_DELAY_EXCHANGE = "chat.delay.exchange";

    // 可靠消息队列
    public static final String CHAT_RELIABLE_MESSAGE_QUEUE = "chat.reliable.message.queue";

    public static final String CHAT_DELAY_MESSAGE_QUEUE = "chat.delay.message.queue";

    public static final String CHAT_DELAY_MESSAGE_KEY = "chat.delay.message.key";

    public static final String CHAT_RELIABLE_MESSAGE_KEY = "chat.reliable.message.key";
}
