package com.chx.chat.controller;

import com.chx.chat.entity.UmsMemberInfoEntity;
import com.chx.chat.http.HttpLoginRequest;
import com.chx.chat.netty.service.WebSocketService;
import com.chx.chat.service.UmsMemberInfoService;
import com.chx.chat.service.impl.UmsMemberInfoServiceImpl;
import com.chx.chat.utils.R;
import com.chx.chat.utils.ResultUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 无敌代码写手
 * @CreateTime: 2023年12月07日
 */
@RestController
@RequestMapping("/ums")
public class UmsController {


    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private UmsMemberInfoService umsMemberInfoService;

    /**
     * 注册用户
     * @param memberInfoEntity
     * @return R
     */
    @PostMapping("/register")
    public R register(@RequestBody UmsMemberInfoEntity memberInfoEntity, BindingResult result){
        if (result.hasErrors()) return R.error(ResultUtils.verify(result).get());
        return umsMemberInfoService.regiseter(memberInfoEntity);
    }

    /**
     * 登录
     * @param uid
     * @return R
     * @throws JsonProcessingException
     */
    @PostMapping("/toLogin")
    public R toLogin(@RequestBody HttpLoginRequest request) throws JsonProcessingException {
        return umsMemberInfoService.toLogin(request);
    }
}