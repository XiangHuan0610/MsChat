package com.chx.chat.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chx.chat.entity.SystemNotifyInfoEntity;

public interface SystemNotifyInfoService extends IService<SystemNotifyInfoEntity> {
    void addNotify(SystemNotifyInfoEntity notifyInfoService);

}
