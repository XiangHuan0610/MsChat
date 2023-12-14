package com.chx.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chx.chat.entity.MessageInfoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
public interface MessageInfoService extends IService<MessageInfoEntity> {
    void messagePersistence(String queue) throws JsonProcessingException;

}
