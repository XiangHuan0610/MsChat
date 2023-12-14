package com.chx.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chx.chat.entity.SystemNotifyInfoEntity;
import com.chx.chat.entity.SystemNotifyOffOnlineInfoEntity;
import com.chx.chat.mapper.SystemNotifyOffOnlineInfoMapper;
import com.chx.chat.service.SystemNotifyOffOnlineInfoServie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 */
@Slf4j
@Service
public class SystemNotifyOffOnlineInfoServiceImpl extends ServiceImpl<SystemNotifyOffOnlineInfoMapper, SystemNotifyOffOnlineInfoEntity> implements SystemNotifyOffOnlineInfoServie {

    @Autowired
    private SystemNotifyOffOnlineInfoMapper offOnlineInfoMapper;


    /**
     * 新增到离线通知表
     * @param notifyInfoEntity
     */
    @Override
    public void addOffOnlineNotify(SystemNotifyInfoEntity notifyInfoEntity) {
        SystemNotifyOffOnlineInfoEntity systemNotifyOffOnlineInfoEntity = new SystemNotifyOffOnlineInfoEntity();
        BeanUtils.copyProperties(notifyInfoEntity,systemNotifyOffOnlineInfoEntity);
        offOnlineInfoMapper.insert(systemNotifyOffOnlineInfoEntity);
    }
}
