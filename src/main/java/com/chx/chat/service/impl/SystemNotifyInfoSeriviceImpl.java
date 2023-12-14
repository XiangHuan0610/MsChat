package com.chx.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chx.chat.entity.SystemNotifyInfoEntity;
import com.chx.chat.mapper.SystemNotifyInfoMapper;
import com.chx.chat.netty.service.WebSocketService;
import com.chx.chat.service.SystemNotifyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
@Slf4j
@Service
public class SystemNotifyInfoSeriviceImpl extends ServiceImpl<SystemNotifyInfoMapper, SystemNotifyInfoEntity> implements SystemNotifyInfoService {

    @Autowired
    private SystemNotifyInfoMapper notifyInfoMapper;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void addNotify(SystemNotifyInfoEntity notifyInfoEntity) {
        notifyInfoMapper.insert(notifyInfoEntity);
        // 通知用户
        webSocketService.friendNotify(notifyInfoEntity);
    }
}
