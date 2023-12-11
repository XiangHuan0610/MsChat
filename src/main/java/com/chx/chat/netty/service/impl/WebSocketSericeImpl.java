package com.chx.chat.netty.service.impl;

import com.chx.chat.constant.RabbitMQCodeConstant;
import com.chx.chat.constant.SystemCodeConstant;
import com.chx.chat.entity.UmsMemberInfoEntity;
import com.chx.chat.mapper.UmsMemberInfoMapper;
import com.chx.chat.netty.entity.CommunicationACK;
import com.chx.chat.netty.entity.Message;
import com.chx.chat.netty.entity.MessageContent;
import com.chx.chat.netty.service.WebSocketService;
import com.chx.chat.netty.util.JsonUtil;
import com.chx.chat.config.RedisService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月06日
 */
@Slf4j
@Component
public class WebSocketSericeImpl implements WebSocketService {

    // 最大重发次数
    private static final Integer MAX_RESEND_COUNT = 5;

    private ConcurrentHashMap<Long, Channel> concurrentHashMap = new ConcurrentHashMap<Long, Channel>();

    // 用户离线队列
    private LinkedBlockingDeque<Long> offLineQueue = new LinkedBlockingDeque<>();

    private static ConcurrentHashMap<String,Message> messageMap = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10); // 创建线程池

    @Autowired
    private RedisService redisService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UmsMemberInfoMapper userInfoMapper;



    @Override
    public void put(Long uid, Channel channel) {
        concurrentHashMap.put(uid,channel);
    }


    /**
     * 发送消息
     * @param message
     */
    @Override
    public void send(Message message) {
        MessageContent msg = message.getMsg();
        // 获取接收消息的用户
        Long rid = msg.getRid();

        // 如果用户离线
        UmsMemberInfoEntity user = userInfoMapper.selectById(rid);
        if (user == null) return;
        if (!concurrentHashMap.containsKey(rid)) {
            log.info("用户离线...");
            if (!offLineQueue.contains(rid)) offLineQueue.add(rid);
            redisService.set(SystemCodeConstant.OFFLINE_USER+rid, message);
            replyACK(concurrentHashMap.get(msg.getSid()),message);
            return;
        }

        // 如果用户在线
        Channel channel = concurrentHashMap.get(rid);
        ChannelPromise channelPromise = channel.newPromise();
        messageMap.put(message.getMsgId(),message);
        rabbitTemplate.convertAndSend(RabbitMQCodeConstant.CHAT_DELAY_EXCHANGE,RabbitMQCodeConstant.CHAT_DELAY_MESSAGE_KEY,message.getMsgId());

        ChannelFuture future = channel.writeAndFlush(new TextWebSocketFrame(JsonUtil.message(message)));
        future.addListener((ChannelFutureListener) listener -> {
            int currentResultCount = 1;
            // 如果发送失败，进行重发
            if (!listener.isSuccess()){
                resendMessage(message,currentResultCount++);
            }
        });
    }

    /**
     * 重发消息
     * @param message
     * @param currentResendCount
     */
    private void resendMessage(Message message, int currentResendCount) {
        if (currentResendCount <= MAX_RESEND_COUNT){
            scheduledExecutorService.schedule(() -> send(message),500, TimeUnit.MILLISECONDS);
        }
        // TODO 如果重试超过5次
    }

    /**
     * 回复ACK
     * @param channel
     * @param message
     */
    private void replyACK(Channel channel,Message message) {
        channel.writeAndFlush(new TextWebSocketFrame(new CommunicationACK(message.getMsgId(),true).toString()));
    }

    /**
     * 获取链接数
     * @return int
     */
    @Override
    public Integer size() {
        return concurrentHashMap.size();
    }

    /**
     * 获取离线消息
     * @param uid
     * @return
     */
    @Override
    public String getOffLineMessage(Long uid)  {
        List<String> caches = redisService.getCacheList(SystemCodeConstant.OFFLINE_USER + uid);
        String msg = JsonUtil.queueParse(caches);
        offLineQueue.remove(uid);
        redisService.deleteObject(SystemCodeConstant.OFFLINE_USER + uid);
        return msg;
    }

    @Override
    public void del200Message(String msgId) {
        messageMap.remove(msgId);
    }

    @Override
    public boolean containsKey(Long uid) {
        return offLineQueue.contains(uid);
    }

    /**
     * 消费者是否收到消息并回信，false为收到消息并回信
     * @param msgId
     * @return
     */
    @Override
    public boolean containsMessageKey(String msgId) {
        return messageMap.containsKey(msgId);
    }

    /**
     * 根据mgsId获取一条消息
     * @param msgId
     * @return
     */
    @Override
    public Message getMessage(String msgId) {
        return messageMap.get(msgId);
    }
}
