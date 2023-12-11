package com.chx.chat.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chx.chat.entity.UmsMemberInfoEntity;
import com.chx.chat.http.HttpLoginRequest;
import com.chx.chat.utils.R;

public interface UmsMemberInfoService extends IService<UmsMemberInfoEntity> {
    R regiseter(UmsMemberInfoEntity memberInfoEntity);

    R toLogin(HttpLoginRequest request);

}
