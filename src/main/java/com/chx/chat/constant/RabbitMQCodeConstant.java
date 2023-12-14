package com.chx.chat.constant;

import com.chx.chat.utils.PageUtils;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
public class RabbitMQCodeConstant {
    public static final String CHAT_USER_EXCHANGE = "chat.user.exchange";

    public static final String CHAT_DELAY_EXCHANGE = "chat.delay.exchange";

    public static final String CHAT_NOTIFY_EXCHAGNE = "chat.notify.exchange";

    public static final String CHAT_MESSAGE_EXCHANGE = "chat.message.exchange";

    /**
     * QUEUE1
     */

    // 消息持久化存盘队列
    public static  final String CHAT_DATA_MESSAGE_QUEUE = "chat.data.message.queue";

    // 验证消息可靠队列
    public static final String CHAT_RELIABLE_MESSAGE_QUEUE = "chat.reliable.message.queue";

    // 通知队列
    public static final String CHAT_NOTIFY_INFO_QUEUE = "chat.notify.info.queue";

    // 死信队列
    public static final String CHAT_DELAY_MESSAGE_QUEUE = "chat.delay.message.queue";


    /**
     * KEY
     */

    public static final String CHAT_DATA_MESSAGE_KEY = "chat.data.message.key";

    public static final String CHAT_NOTIFY_CHECK_KEY = "chat.notify.check.key";

    public static final String CHAT_DELAY_MESSAGE_KEY = "chat.delay.message.key";

    public static final String CHAT_RELIABLE_MESSAGE_KEY = "chat.reliable.message.key";
}
