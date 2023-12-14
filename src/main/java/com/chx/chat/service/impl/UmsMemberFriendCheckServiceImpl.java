package com.chx.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chx.chat.constant.RabbitMQCodeConstant;
import com.chx.chat.constant.SystemCodeConstant;
import com.chx.chat.constant.SystemNotifyCodeConstant;
import com.chx.chat.entity.SystemNotifyInfoEntity;
import com.chx.chat.entity.UmsMemberFriendCheckEntity;
import com.chx.chat.entity.UmsMemberInfoEntity;
import com.chx.chat.enums.AppHttpCodeEnum;
import com.chx.chat.http.HttpAddFriendRequest;
import com.chx.chat.mapper.UmsMemberFriendCheckMapper;
import com.chx.chat.mapper.UmsMemberInfoMapper;
import com.chx.chat.service.UmsMemberFriendCheckService;
import com.chx.chat.utils.R;
import com.chx.chat.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月11日
 *
 */
@Slf4j
@Service
public class UmsMemberFriendCheckServiceImpl extends ServiceImpl<UmsMemberFriendCheckMapper, UmsMemberFriendCheckEntity> implements UmsMemberFriendCheckService {
    @Autowired
    private UmsMemberFriendCheckMapper checkMapper;

    @Autowired
    private UmsMemberInfoMapper infoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public R addFriend(HttpAddFriendRequest request) {
        UmsMemberInfoEntity umsMemberInfoEntity = infoMapper.selectOne(new QueryWrapper<UmsMemberInfoEntity>().eq("upt", request.getCode()));
        if (umsMemberInfoEntity == null) return R.error().put(AppHttpCodeEnum.USER_IS_NULL.getMsg());
        UmsMemberInfoEntity user = infoMapper.selectById(request.getUid());
        SystemNotifyInfoEntity systemNotifyInfoEntity = new SystemNotifyInfoEntity(null,request.getUid(), umsMemberInfoEntity.getId(),
                SystemNotifyCodeConstant.ADD_FRIEND_NOTIFY, createTitle(user), request.getContent(),
                SystemNotifyCodeConstant.ADD_FRIEND_STATE0_CODE, TimeUtil.now(), TimeUtil.now(), 0);

        // 发送添加好友通知
        rabbitTemplate.convertAndSend(RabbitMQCodeConstant.CHAT_NOTIFY_EXCHAGNE,RabbitMQCodeConstant.CHAT_NOTIFY_CHECK_KEY,systemNotifyInfoEntity);

        return R.ok().put(SystemNotifyCodeConstant.ADD_FRIEND_RETURN_CONTENT);
    }

    private String createTitle(UmsMemberInfoEntity user) {
        return user.getName() + SystemNotifyCodeConstant.ADD_FRIEND_NOTIFY_TITILE;
    }
}
