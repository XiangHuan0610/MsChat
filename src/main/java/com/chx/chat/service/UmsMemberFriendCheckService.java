package com.chx.chat.service;

import com.baomidou.mybatisplus.extension.ddl.IDdl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chx.chat.entity.UmsMemberFriendCheckEntity;
import com.chx.chat.http.HttpAddFriendRequest;
import com.chx.chat.utils.R;

public interface UmsMemberFriendCheckService extends IService<UmsMemberFriendCheckEntity> {
    R addFriend(HttpAddFriendRequest request);
}
