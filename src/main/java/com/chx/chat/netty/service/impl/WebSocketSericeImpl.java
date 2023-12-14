package com.chx.chat.netty.service.impl;

import com.chx.chat.constant.RabbitMQCodeConstant;
import com.chx.chat.constant.SystemCodeConstant;
import com.chx.chat.constant.SystemNotifyCodeConstant;
import com.chx.chat.entity.SystemNotifyInfoEntity;
import com.chx.chat.entity.UmsMemberInfoEntity;
import com.chx.chat.mapper.SystemNotifyInfoMapper;
import com.chx.chat.mapper.SystemNotifyOffOnlineInfoMapper;
import com.chx.chat.mapper.UmsMemberInfoMapper;
import com.chx.chat.netty.entity.CommunicationACK;
import com.chx.chat.netty.entity.Message;
import com.chx.chat.netty.entity.MessageContent;
import com.chx.chat.netty.service.WebSocketService;
import com.chx.chat.netty.util.JsonUtil;
import com.chx.chat.config.redis.RedisService;
import com.chx.chat.service.SystemNotifyOffOnlineInfoServie;
import com.chx.chat.utils.Query;
import com.chx.chat.utils.TimeUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    // 消息阈值
    private static final Integer MAX_MESSAGE_COUNT = 3;

    // 链接通道Map
    private ConcurrentHashMap<Long, Channel> concurrentHashMap = new ConcurrentHashMap<Long, Channel>();

    // 用户离线队列
    private LinkedBlockingQueue<Long> offLineQueue = new LinkedBlockingQueue<>();

    // 消息队列
    private LinkedBlockingQueue<Message> msgQueue = new LinkedBlockingQueue<Message>(MAX_MESSAGE_COUNT);

    // 消息Map
    private static ConcurrentHashMap<String,Message> messageMap = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10); // 创建线程池

    @Autowired
    private RedisService redisService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UmsMemberInfoMapper userInfoMapper;

    @Autowired
    private SystemNotifyInfoMapper notifyInfoMapper;

    @Autowired
    private SystemNotifyOffOnlineInfoMapper offOnlineInfoMapper;

    @Autowired
    private SystemNotifyOffOnlineInfoServie offOnlineInfoServie;


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


        UmsMemberInfoEntity user = userInfoMapper.selectById(rid);
        if (user == null) return;
        // 如果用户离线
        if (!concurrentHashMap.containsKey(rid)) {
            log.info("用户离线...");
            if (!offLineQueue.contains(rid)) offLineQueue.add(rid); // 判断是否再离线队列，如果不在则添加
            if (!redisService.hasKey(SystemCodeConstant.OFFLINE_USER_EXPIRATION + rid)) redisService.setCacheObject(SystemCodeConstant.OFFLINE_USER_EXPIRATION + rid,"del",20l,TimeUnit.SECONDS);
            redisService.setList(SystemCodeConstant.OFFLINE_USER_KEY+rid,message); // 离线消息存redis
            replyACK(concurrentHashMap.get(msg.getSid()),message);
            return;
        }

        // 如果用户在线
        Channel channel = concurrentHashMap.get(rid);
        ChannelPromise channelPromise = channel.newPromise();
        messageMap.put(message.getMsgId(),message);
        if (msgQueue.size() >= MAX_MESSAGE_COUNT) {
            // 如果队列中消息达到阈值，则进行持久化操作
            ArrayList<Message> list = new ArrayList<Message>(msgQueue);
            rabbitTemplate.convertAndSend(RabbitMQCodeConstant.CHAT_MESSAGE_EXCHANGE,RabbitMQCodeConstant.CHAT_DATA_MESSAGE_KEY,JsonUtil.message(msgQueue));
            msgQueue.clear();
        }
        msgQueue.offer(message);
        rabbitTemplate.convertAndSend(RabbitMQCodeConstant.CHAT_DELAY_EXCHANGE,RabbitMQCodeConstant.CHAT_DELAY_MESSAGE_KEY,message.getMsgId());

        ChannelFuture future = channel.writeAndFlush(new TextWebSocketFrame(JsonUtil.message(message)));
        future.addListener((ChannelFutureListener) listener -> {
            int currentResultCount = 1;
            // 如果发送失败，进行重发
            if (!listener.isSuccess()){
                resendMessage(message,currentResultCount++);
            }
        });
        del200Message(message.getMsgId());
    }

    /**
     * 发送添加好友通知
     * @param notifyInfoEntity
     */
    @Override
    public void friendNotify(SystemNotifyInfoEntity notifyInfoEntity) {
        Long rid = notifyInfoEntity.getRid();
        UmsMemberInfoEntity umsMemberInfoEntity = userInfoMapper.selectById(rid);
        if (umsMemberInfoEntity == null) return;

        if (!concurrentHashMap.containsKey(rid)){
            log.info("用户不在线");
            return;
        }
        log.info("用户在线");
        Channel channel = concurrentHashMap.get(rid);
        channel.writeAndFlush(new TextWebSocketFrame(JsonUtil.message(notifyInfoEntity)));
        notifyInfoEntity.setState(SystemNotifyCodeConstant.ADD_FRIEND_STATE1_CODE);
        notifyInfoMapper.updateById(notifyInfoEntity);
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
        List<String> caches = redisService.getCacheList(SystemCodeConstant.OFFLINE_USER_KEY + uid);
        String msg = JsonUtil.queueParse(caches);
        offLineQueue.remove(uid);
        redisService.deleteObject(SystemCodeConstant.OFFLINE_USER_KEY + uid);
        return msg;
    }

    /**
     * 删除发送成功的消息
     * @param msgId
     */
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
