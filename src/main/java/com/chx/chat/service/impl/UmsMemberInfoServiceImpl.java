package com.chx.chat.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chx.chat.config.RedisService;
import com.chx.chat.constant.SystemCodeConstant;
import com.chx.chat.entity.UmsMemberInfoEntity;
import com.chx.chat.enums.AppHttpCodeEnum;
import com.chx.chat.http.HttpLoginRequest;
import com.chx.chat.mapper.UmsMemberInfoMapper;
import com.chx.chat.netty.service.WebSocketService;
import com.chx.chat.service.UmsMemberInfoService;
import com.chx.chat.utils.JwtUtil;
import com.chx.chat.utils.R;
import com.chx.chat.utils.RSAEncryptionUtil;
import com.chx.chat.utils.TimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月07日
 */
@Service
public class UmsMemberInfoServiceImpl extends ServiceImpl<UmsMemberInfoMapper, UmsMemberInfoEntity> implements UmsMemberInfoService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UmsMemberInfoMapper umsMemberInfoMapper;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public R regiseter(UmsMemberInfoEntity memberInfoEntity) {
        UmsMemberInfoEntity member = umsMemberInfoMapper.selectOne(new QueryWrapper<UmsMemberInfoEntity>().eq("upt", memberInfoEntity.getUpt()));
        if (member != null) return R.error().put(AppHttpCodeEnum.USER_IS_NOT_NULL.getMsg());

        UmsMemberInfoEntity umsMemberInfoEntity = createUser(memberInfoEntity);
        umsMemberInfoMapper.insert(umsMemberInfoEntity);
        String token = createToken(umsMemberInfoEntity);
        redisService.setCacheObject(SystemCodeConstant.USER_INFO_PREFIX + umsMemberInfoEntity.getUpt(),token);
        return R.ok().put(AppHttpCodeEnum.REGISTER_SUCCESS.getMsg());
    }

    @Override
    public R toLogin(HttpLoginRequest request) {
        String token = (String) redisService.getCacheObject(SystemCodeConstant.USER_INFO_PREFIX + request.getUpt());

        if (StringUtils.isEmpty(token)){
            UmsMemberInfoEntity umsMemberInfoEntity = umsMemberInfoMapper.selectOne(new QueryWrapper<UmsMemberInfoEntity>().eq("upt", request.getUpt()));
            if (umsMemberInfoEntity == null) return R.error().put(AppHttpCodeEnum.USER_IS_NULL.getMsg());

            if (RSAEncryptionUtil.decryptRAS(umsMemberInfoEntity.getPassword()).equals(request.getPassword())){
                token = createToken(umsMemberInfoEntity);
                redisService.setCacheObject(SystemCodeConstant.USER_INFO_PREFIX+request.getUpt(),token);
                umsMemberInfoEntity.setLoginTime(TimeUtil.now());
                umsMemberInfoMapper.updateById(umsMemberInfoEntity);
                String offLineMessage = webSocketService.getOffLineMessage(umsMemberInfoEntity.getId());
                return R.ok().login(umsMemberInfoEntity,offLineMessage,token);
            }
            return R.error().put(AppHttpCodeEnum.LOGIN_ERROR.getMsg());
        }
        DecodedJWT verify = JwtUtil.verify(token);
        String offLineMessage = webSocketService.getOffLineMessage(Long.valueOf(verify.getClaim("uid").asString()));
        System.out.println("offlineMessage: " + offLineMessage);
        return R.ok().login(offLineMessage,token);
    }

    private String createToken(UmsMemberInfoEntity umsMemberInfoEntity) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid",umsMemberInfoEntity.getId().toString());
        map.put("upt",umsMemberInfoEntity.getUpt());
        map.put("name",umsMemberInfoEntity.getName());
        String token = JwtUtil.getToken(map);
        return token;
    }

    private UmsMemberInfoEntity createUser(UmsMemberInfoEntity memberInfoEntity) {
        return new UmsMemberInfoEntity(null, memberInfoEntity.getUpt(), memberInfoEntity.getUpt(),
                RSAEncryptionUtil.encryptRSA(memberInfoEntity.getPassword()),
                "", "", TimeUtil.now(), TimeUtil.now(), TimeUtil.now(), 0);
    }
}
