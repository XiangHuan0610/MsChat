package com.chx.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chx.chat.entity.MessageInfoEntity;
import com.chx.chat.mapper.MessageInfoMapper;
import com.chx.chat.netty.entity.Message;
import com.chx.chat.netty.entity.MessageContent;
import com.chx.chat.netty.util.JsonUtil;
import com.chx.chat.service.MessageInfoService;
import com.chx.chat.utils.R;
import com.chx.chat.utils.TimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfoEntity> implements MessageInfoService {
    @Autowired
    private MessageInfoMapper messageInfoMapper;

    @Override
    public void messagePersistence(String json) throws JsonProcessingException {
        LinkedBlockingQueue<Message> queue = JsonUtil.blockingQueueParse(json);
        List<MessageInfoEntity> messages = queue.stream().map(item -> { return buildMessage(item);
        }).collect(Collectors.toList());

        this.saveBatch(messages);
    }

    private MessageInfoEntity buildMessage(Message item) {
        MessageContent msg = item.getMsg();
        MessageInfoEntity messageInfoEntity = new MessageInfoEntity(null,item.getMsgId(),msg.getSid(),msg.getRid(),item.getType(),
                msg.toString(),msg.getSendTime(), TimeUtil.now(),0);
        return messageInfoEntity;
    }
}
