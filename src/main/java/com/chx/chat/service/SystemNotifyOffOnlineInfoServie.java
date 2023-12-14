package com.chx.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chx.chat.entity.SystemNotifyInfoEntity;
import com.chx.chat.entity.SystemNotifyOffOnlineInfoEntity;

public interface SystemNotifyOffOnlineInfoServie extends IService<SystemNotifyOffOnlineInfoEntity> {
    void addOffOnlineNotify(SystemNotifyInfoEntity notifyInfoEntity);
}
